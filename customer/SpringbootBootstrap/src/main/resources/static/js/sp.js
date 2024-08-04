$(document).ready(function () {
    detailSP();
    fillImageSP();
    deleteImg();
    fakeInputFileAdd();
    ipFSubmitForm();
    addImg();

})

function fakeInputFileAdd() {
    $('.btn-fecht-input').on('click', function () {
        $(this).siblings('.imageIP').click();
    });
};


function detailSP() {
    $('#table1 .uBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (sp) {
            var taoLucFormat = moment(sp.taoLuc).format("YYYY-MM-DD HH:mm:ss");
            var suaLucFormat = moment(sp.suaLuc).format("YYYY-MM-DD HH:mm:ss");
            $('.formUpdate #ma').val(sp.ma);
            $('.formUpdate #ten').val(sp.ten);
            $('.formUpdate #danhMuc').val(sp.danhMuc.id);
            $('.formUpdate #thuongHieu').val(sp.thuongHieu.id);
            $('.formUpdate #lbTaoLuc').text("Tạo lúc: "+taoLucFormat);
            $('.formUpdate #lbSuaLuc').text("Sửa lúc: "+(sp.suaLuc==null?"":suaLucFormat));
            $('.formUpdate #lbTaoBoi').text("Tạo bởi: "+sp.taoBoi);
            $('.formUpdate #lbSuaBoi').text("Sửa bởi: "+(sp.suaBoi==null?"":sp.suaBoi));
            $('.formUpdate #taoLuc').val(sp.taoLuc);
            $('.formUpdate #taoBoi').val(sp.taoBoi);

            $('.formUpdate #trangThaiUpdate').prop('checked', sp.trangThai);
            $('.formUpdate #id').val(sp.id);
        })
        $('.formUpdate #suaSP').modal('show');
    })
};

function ipFSubmitForm() {
    $('.imageIP').on('change', function (event) {
        var slImg = $('.image-container').length;
        var files = event.target.files;
        if (files.length > 6) {
            Swal.fire({
                title: "ERROR!",
                text: "Bạn chỉ được chọn tối đa 6 file ảnh",
                icon: "error"
            });
            $(this).val('');
            return;
        }

        if ((files.length + slImg )>6) {
            Swal.fire({
                title: "ERROR!",
                text: "Sản phẩm có tối đa 6 ảnh, chỉ có thể chọn thêm "+(6-slImg)+" file",
                icon: "error"
            });
            $(this).val('');
            return;
        }
        $("#formAddImage").submit();
    });


}

function addImg() {
    $("#formAddImage").submit(function (e) {
        e.preventDefault();
        var formData = new FormData();
        var idSP = $("#idSPImg").val();

        var files = $('.imageIP')[0].files;
        for (var i = 0; i < files.length; i++) {
            formData.append('file', files[i]);
        }
        formData.append("idSP", idSP);

        Swal.fire({
            title: "Thêm ảnh?",
            text: "Bạn có chắc muốn thêm các ảnh này?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Có,tôi chắc chắn"
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: "POST",
                    url: "/san_pham/addImage",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (response) {
                        console.log(response);
                        $.each(response, function (index, img) {
                            cvImg(img);
                        });

                    },
                    error: function (xhr, status, error) {
                        alert("Đã xảy ra lỗi khi tạo sản phẩm!");
                        console.log(xhr.responseText); // In ra nội dung lỗi từ phản hồi
                        console.log(status); // In ra trạng thái lỗi
                        console.log(error); // In ra thông tin lỗi
                    }
                });
                Swal.fire({
                    title: "Thành công!",
                    text: "Đã tạo sản phẩm thành công.",
                    icon: "success"
                });
            }
        });


    });


}

function deleteImg() {
    $('#fillImage').on('click', '.rmImg', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var id = href.substring(href.lastIndexOf('/') + 1);
        var imgParent = $(this).closest('#imgParent' + id);
        var slImg = $('.image-container').length;
        if (slImg <= 1) {
            Swal.fire({
                title: "ERROR!",
                text: "Cần ít nhất 1 ảnh cho sản phẩm",
                icon: "error"
            });
            return;
        }
        Swal.fire({
            title: "Are you sure?",
            text: "Ảnh của sản phẩm sẽ được xóa khi bạn cho phép",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: 'GET',
                    url: href,
                    success: function (response) {
                        imgParent.remove()
                        Swal.fire({
                            title: "Deleted!",
                            text: "Bạn vừa xóa thành công 1 ảnh",
                            icon: "success"
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error('Error:', error);
                    }
                });
            }
        });


    })
};

function fillImageSP() {
    $('#table1 .iBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (data) {
            $("#fillImage").empty();
            $("#lbsuaImage").empty();
            $("#idSPImg").val(data[0].sanPham.id);
            $("#lbsuaImage").append(`
             Image Sản Phẩm-${data[0].sanPham.ma}`)
            $.each(data, function (index, img) {
                cvImg(img);


            });
        })

        $('.formUpdateImage #suaImage').modal('show');
    })
};

function cvImg(img) {
    var imgP = 'imgParent' + img.id;
    $.ajax({
        type: 'GET',
        url: '/san_pham/convertToBase64?id=' + img.id,
        success: function (response) {
            $("#fillImage").append(`
                <div class="col-md-2 image-container" id="${imgP}">
                <img style="width: 100px;height: 100px" src="data:image/jpeg;base64,${response}">
                <div class="text-overlay">
       
                <a class="rmImg" href="/san_pham/deleteImage/${img.id}" type="button"><i data-feather="trash"></i></a>
                
    
                </div>
                
                </div>
                `);
            feather.replace();
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }

    });

}