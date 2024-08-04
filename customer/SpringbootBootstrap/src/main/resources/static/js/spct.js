$(document).ready(function () {
    getOneSPCT();
    valiDateThemSPCT();
    valiDateSuaSPCT();
    $('#vlidMS').on('change',function () {
        updateSelectBackgroundColorAdd();
    });
    $('#mauSacUpd').on('change',function () {
        updateSelectBackgroundColorUpd();
    });
    $('#slMS').on('change',function () {
        updateSelectBackgroundColor();
    });
    updateSelectBackgroundColorAdd();
    updateSelectBackgroundColorUpd();

});



function updateSelectBackgroundColorAdd() {
    var selectedOption = $('#vlidMS option:selected');
    var selectedColor = selectedOption.css('background-color');
    $('#vlidMS').css('background-color', selectedColor);
}
function updateSelectBackgroundColor() {
    var selectedOption = $('#slMS option:selected');
    var selectedColor = selectedOption.css('background-color');
    $('#slMS').css('background-color', selectedColor);
}

function updateSelectBackgroundColorUpd() {
    var selectedOption = $('#mauSacUpd option:selected');
    var selectedColor = selectedOption.css('background-color');
    $('#mauSacUpd').css('background-color', selectedColor);
}

function updatePagination(data) {
    var totalPages = data.totalPages;
    var currentPage = data.number + 1; // Trang hiện tại (đánh số từ 1)

    $('#page').empty();

    // Chỉ hiển thị nút "Previous" nếu không phải là trang đầu tiên
    if (currentPage > 1) {
        var prevPageLink = $('<a>', {
            class: 'page-link',
            href: '#',
            'data-page': currentPage - 2
        }).text('Previous');
        var prevPageItem = $('<li>', {
            class: 'page-item'
        }).append(prevPageLink);
        $('#page').append(prevPageItem);
    }

    // Hiển thị số trang từ trang hiện tại - 1 đến trang hiện tại + 1
    var startPage = Math.max(1, currentPage - 1);
    var endPage = Math.min(totalPages, currentPage + 1);

    for (var i = startPage; i <= endPage; i++) {
        var pageLink = $('<a>', {
            class: 'page-link',
            href: '#',
            'data-page': i - 1
        }).text(i);
        var listItem = $('<li>', {
            class: 'page-item ' + (i === currentPage ? 'active' : '')
        }).append(pageLink);
        $('#page').append(listItem);
    }

    // Chỉ hiển thị nút "Next" nếu không phải là trang cuối cùng
    if (currentPage < totalPages) {
        var nextPageLink = $('<a>', {
            class: 'page-link',
            href: '#',
            'data-page': currentPage
        }).text('Next');
        var nextPageItem = $('<li>', {
            class: 'page-item'
        }).append(nextPageLink);
        $('#page').append(nextPageItem);
    }


}




function getOneSPCT() {
    $('#table1 .uBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.ajax({
            type: 'GET', // Sử dụng phương thức GET để lấy dữ liệu spct từ href
            url: href, // Gửi yêu cầu GET đến URL được chỉ định trong thuộc tính href
            success: function (spct) {

                $('.formUpdate #idSPCTUpd').val(spct.id);
                $('.formUpdate #kichThuocUpd').val(spct.kichThuoc.id);
                $('.formUpdate #maSPCTUpd').val(spct.ma);
                $('.formUpdate #mauSacUpd').val(spct.mauSac.id);
                $('.formUpdate #msUpd').val(spct.mauSac.id);
                $('.formUpdate #ktUpd').val(spct.kichThuoc.id);
                $('.formUpdate #soLuongUpd').val(spct.sl);
                $('.formUpdate #ipFileFake').val(spct.data);
                $('.formUpdate #donGiaUpd').val(spct.gia);
                $('.formUpdate #tgTao').val(spct.taoLuc);
                $('.formUpdate #mauSacUpd').trigger('change');
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
        $('.formUpdate #suaSPCT').modal('show');
    })

}

function valiDateSuaSPCT() {

    $("#cfUpdBtn").click(function (e) {

        if (!validateSLUpd() || !validateGiaUpd()) {

            e.preventDefault();
            return;
        }
        e.preventDefault();

        // Hiển thị thông báo xác nhận
        showConFirmUpd();
    });


}

function valiDateThemSPCT() {

    $("#cfAddBtn").click(function (e) {

        if ( !validateSoLuongAdd() || !validateGiaAdd() || !validateMSAndKT()) {

            e.preventDefault();
            return;
        }
        e.preventDefault();

        // Hiển thị thông báo xác nhận
        showConFirmAdd();
    });


}


function showConFirmUpd() {
    Swal.fire({
        title: "Are you sure?",
        text: "Bạn có chắc muốn sửa sản phẩm chi tiết này?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes"
    }).then((result) => {
        if (result.isConfirmed) {
            $("#formUpdSPCT").submit();
            Swal.fire({
                position: "top-end",
                icon: "success",
                title: "Cập nhật sản phẩm chi tiết  thành công",

            });
        }
    });

}

function showConFirmAdd() {
    Swal.fire({
        title: "Are you sure?",
        text: "Bạn có muốn thêm sản phẩm chi tiết mới không?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes!"
    }).then((result) => {
        if (result.isConfirmed) {
            $("#formAddSPCT").submit();
            Swal.fire({
                position: "top-end",
                icon: "success",
                title: "Tạo sản phẩm chi tiết mới thành công",

            });
        }
    });

}

function validateGiaUpd() {
    var gia = document.querySelector("#donGiaUpd").value;
    if (gia.trim() === "" || isNaN(gia)) {
        Swal.fire({
            title: "Error!",
            text: "Vui lòng nhập giá hợp lệ",
            icon: "warning",

        });
        return false;
    }
    if (gia <= 0) {
        Swal.fire({
            title: "Error!",
            text: "Giá sản phẩm phải lớn hơn 0 ",
            icon: "warning",

        });
        return false;
    }
    return true;
}
function validateSLUpd() {
    var gia = document.querySelector("#soLuongUpd").value;
    if (gia.trim() === "" || isNaN(gia)) {
        Swal.fire({
            title: "Error!",
            text: "Vui lòng nhập số lượng hợp lệ",
            icon: "warning",

        });
        return false;
    }
    if (gia <= 0) {
        Swal.fire({
            title: "Error!",
            text: "Số lượng sản phẩm phải lớn hơn 0 ",
            icon: "warning",

        });
        return false;
    }
    return true;
}

function validateMSAndKT() {
    var ktAdd = document.getElementById("vlidKT").value.trim();
    var msAdd = document.getElementById("vlidMS").value.trim();
    var spAdd = document.getElementById("idSPAdd").value.trim();

    $.ajax({
        url: "/spct/checkMSAndKTSPCT",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({idMS: msAdd, idKT: ktAdd,idSP: spAdd}),
        success: function (data) {
            if (data) {
                Swal.fire({
                    title: "Error!",
                    text: "Sản phẩm chi tiết này đã tồn tại",
                    icon: "warning"
                });
                return false;
            }

        },
        error: function (xhr, status, error) {
            console.error("Error:", error);
        }

    });
    return true;
}




function validateGiaAdd() {
    var gia = document.querySelector("#dgAdd").value;
    if (gia.trim() === "" || isNaN(gia)) {
        Swal.fire({
            title: "Error!",
            text: "Vui lòng nhập giá hợp lệ",
            icon: "warning",

        });
        return false;
    }
    if (gia <= 0) {
        Swal.fire({
            title: "Error!",
            text: "Giá sản phẩm phải lớn hơn 0 ",
            icon: "warning",

        });
        return false;
    }
    return true;
}

function validateSoLuongAdd() {
    var sl = document.querySelector("#slAdd").value;
    if (sl.trim() === "" || isNaN(sl)) {
        Swal.fire({
            title: "Error!",
            text: "Vui lòng nhập số lượng hợp lệ ",
            icon: "warning",

        });
        return false;
    }
    if (sl <= 0) {
        Swal.fire({
            title: "Error!",
            text: "Số lượng sản phẩm phải lớn hơn 0 ",
            icon: "warning",

        });
        return false;
    }

    return true;
}



