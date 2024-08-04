$(document).ready(function () {

    findOneSP();
    renderMSandKT();
    findTTSP();


});
function formatCurrency(value) {
    var number = Number(value);
    var formattedNumber = number.toLocaleString('vi-VN', { minimumFractionDigits: 0, maximumFractionDigits: 0 });
    return formattedNumber + ' VNĐ';
}

function findOneSP() {
    $('.product-gallery-item').click(function clickImg() {
        $('.product-gallery-item').removeClass('active');
        $(this).addClass('active')
    })
}

function findTTSP() {
    var idSP = $('#idSP').val();

    $.ajax({
        type: 'GET',
        url: '/spOnl/sp/?id=' + idSP,
        success: function (data) {

            $('#nameSP').empty();
            $('#nameSP').append(`
            ${data.ten}
            `)
            $('#danhMuc_thuongHieu').empty();
            $('#danhMuc_thuongHieu').append(`
                                    <p>Danh mục: ${data.danhMuc.ten}</p>
                                    <br>
                                    <p>Thương hiệu: ${data.thuongHieu.ten}</p>
            `)
            $('#giaSP').empty();

            var minPrice = 0;
            var maxPrice = 0;

            $.ajax({
                type: 'GET',
                url: '/spOnl/gia_min?id=' + data.id,
                success: function (dgmin) {
                    minPrice = formatCurrency(dgmin);
                    console.log(minPrice);
                }
            });
            $.ajax({
                type: 'GET',
                url: '/spOnl/gia_max?id=' + data.id,
                success: function (dgmax) {
                    maxPrice = formatCurrency(dgmax);

                    console.log(maxPrice);
                }
            });

            $.ajax({
                type: 'GET',
                url: '/spOnl/anh?id=' +idSP,
                success: function (imgdata) {
                    $('#giaSP').append(`  ${minPrice} - ${maxPrice}`)
                    $('.carousel-inner').empty();
                    $('.carousel-indicators').empty();
                    $.each(imgdata, function (index, img) {
                        console.log("id:"+index)
                        var isFirst = index === 0;
                        $.ajax({
                            type: 'GET',
                            url: '/spOnl/convertToBase64?id=' +img.id,
                            success: function (response) {
                                $('.carousel-indicators').append(`
                                    <button type="button" data-bs-target="#carouselExampleFade" data-bs-slide-to="${index}"
                                    ${isFirst ? 'class="active"' : ''} aria-label="Slide ${index+1}"></button>
                                      `);

                                $('.carousel-inner').append(`
                             <div class="carousel-item ${isFirst ? 'active' : ''}">
                             
                                  <img style="max-width: 514px;min-width: 514px;max-height: 514px;min-height: 514px;" src="data:image/jpeg;base64,${response}"
                                   class="d-block w-100" alt="...">                                                                                                  
                             </div>
                            `)

                            },
                            error: function (xhr, status, error) {
                                console.error('Error:', error);
                            }

                        });
                    })


                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }

            });


        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }

    });
    $.ajax({
        type: 'GET',
        url: '/spOnl/ktSP?id=' + idSP,
        success: function (data) {
            $('#sizeSP').empty();
            $.each(data, function (index, kt) {
                $('#sizeSP').append(`
                <option value="${kt.id}">${kt.ten}</option>
                `)
            });

        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }

    });
}

function renderMSandKT() {

    var idSP = $('#idSP').val();
    $.ajax({
        type: 'GET',
        url: '/spOnl/mauSP?id=' + idSP,
        success: function (data) {

            $('#msSP').empty();
            $.each(data, function (index, ms) {

                $('#msSP').append(`
                <a class="clMS" type="button"  style="background: ${ms.ten}"> <input id="msSPCT${ms.id}" value="${ms.id}" hidden></a>`)
            });
            $('.clMS').click(function () {
                // Xóa lớp active từ tất cả các phần tử <a> khác
                $('.clMS').removeClass('active');
                // Thêm lớp active cho phần tử được nhấp vào
                $(this).addClass('active');
            });


        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }

    });
    $.ajax({
        type: 'GET',
        url: '/spOnl/ktSP?id=' + idSP,
        success: function (data) {
            $('#sizeSP').empty();
            $.each(data, function (index, kt) {
                $('#sizeSP').append(`
                <option value="${kt.id}">${kt.ten}</option>
                `)
            });

        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }

    });

}

$(document).ready(function () {
    var slTon = 0;

    function fetchGiaSPCT() {
        var idSP = $('#idSP').val();
        var idMS = $('.clMS.active').find('input').val(); // Lấy idMS từ .clMS đang active
        var idKT = $('#sizeSP').val();

        $.ajax({
            type: 'GET',
            url: '/spOnl/spctGH?id=' + idSP + '&idMS=' + idMS + '&idKT=' + idKT,
            success: function (dtspct) {
                var dgSPCT=formatCurrency(dtspct.gia);
                $('#idSPCT').val(dtspct.id);
                console.log('idspct:' + $('#idSPCT').val());
                $('#slTon').empty();
                $('#slTon').append(` <p>Số lượng tồn: </p> <p>${dtspct.sl}</p>`);
                $('#giaSP').empty();
                $('#giaSP').append(dgSPCT);

                slTon = dtspct.sl;
            },

            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }

    $(document).on('click', '.clMS', function (e) {
        var idSP = $('#idSP').val();
        console.log('idsp:' + idSP);
        $('.clMS').removeClass('active');
        $(this).addClass('active');
        var idMS = $(this).find('input').val();
        console.log(idMS);
        $.ajax({
            type: 'GET',
            url: '/spOnl/ktSPCT?id=' + idSP + '&idMS=' + idMS,
            success: function (dataSPCT) {
                $('#sizeSP').empty();
                $.each(dataSPCT, function (index, spct) {
                    $('#sizeSP').append(`
                        <option value="${spct.kichThuoc.id}">${spct.kichThuoc.ten}</option>
                    `);
                });
                // Gọi lại fetchData khi đã có giá trị mới của idKT
                fetchGiaSPCT();
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
    });

    // Gọi fetchData khi giá trị của #sizeSP thay đổi
    $('#sizeSP').change(function () {
        fetchGiaSPCT();
    });
    $('#qty').change(function () {
        var input = $(this).val(); // Lấy giá trị của input được thay đổi
        if ((input !== '' && !isNaN(input))) {
            // Nếu giá trị nhập vào là số và không rỗng
        } else {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Vui lòng nhập số !",
            });
            $(this).val(1); // Đặt lại giá trị về 1 nếu không phải số hoặc rỗng
        }
    });


    $('#formAddGH').submit(function (e) {
        e.preventDefault();
        var idSPCT = $('#idSPCT').val();
        var isClicked = $('.clMS').hasClass('active');
        var slGH = 0;
        console.log("slTon:" + slTon)
        var sl = parseInt($('#qty').val());

        if (!isClicked) {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Vui lòng chọn màu sắc và kích thước trước khi hêm vào giỏ!",
            });
            return;
        }
        if (sl > slTon) {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Sản phẩm này chỉ còn " + slTon + " sản phẩm",
            });
            return;
        }
        $.ajax({
            type: 'GET',
            url: '/shop/getGHCTBySPCT?idSPCT=' + idSPCT ,
            success: function (dt) {
                slGH=dt.soLuong;
                if ((sl+slGH) > slTon) {
                    console.log('slValid:'+(sl+slGH))
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: "Bạn chỉ có thể mua thêm " + (slTon-slGH) + " sản phẩm do giỏ hàng của bạn đã có "+slGH+" sản phầm này",
                    });
                    return;
                }
                Swal.fire({
                    title: "Are you sure?",
                    text: "Bạn có chắc muốn thêm sản phẩm này vào giỏ không?",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Có"
                }).then((result) => {
                    if (result.isConfirmed) {

                        $.ajax({
                            type: "POST",
                            url:  "/shop/addGH",
                            data: JSON.stringify({
                                idSPCT: idSPCT,
                                soLuong: sl,

                            }),
                            contentType: "application/json",
                            dataType: "json",
                            success: function (response) {
                                Swal.fire({
                                    title: "Thành công!",
                                    text: "Bạn vừa thêm 1 sản phẩm vào giỏ",
                                    icon: "success"
                                });
                                window.location.reload();
                            },
                            error: function (xhr, status, error) {
                                Swal.fire({
                                    title: "Thất bại!",
                                    text: "Bạn cần đăng nhập để thêm vào giỏ hàng",
                                    icon: "error",
                                    showCancelButton: true,
                                    confirmButtonColor: "#3085d6",
                                    cancelButtonColor: "#d33",
                                    confirmButtonText: "Login"
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        window.location.href="http://localhost:8081/login";
                                    }
                                });


                                console.log(xhr.responseText); // In ra nội dung lỗi từ phản hồi
                                console.log(status); // In ra trạng thái lỗi
                                console.log(error); // In ra thông tin lỗi
                            }
                        });

                    }
                });

            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });



    })


});





