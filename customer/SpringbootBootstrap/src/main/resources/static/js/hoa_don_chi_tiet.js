//fill table SanPham , phân trang, tìm kiếm
$(document).ready(function () {
    spctAll();
    messegesXN();
    messegesXNShip();
    messegesCancel();
    messegesSuaSPCT();
    messegesXoaSPCT();
    messegesAddGH();
})

function formatCurrency(value) {
    var number = Number(value);
    var formattedNumber = number.toLocaleString('vi-VN', { minimumFractionDigits: 0, maximumFractionDigits: 0 });
    return formattedNumber + ' VNĐ';
}
function spctAll() {
    var isSearching = false;
    var isFillter = false;
    fetchProducts(0);
    //filter
    $('#formFilter').submit(function (e) {
        e.preventDefault();
        var idKichThuoc = $('#kichThuocSearch').val();
        var idMauSac = encodeURIComponent($('#mauSacSearch').val());
        var idDanhMuc = $('#danhMucSearch').val();
        var idThuongHieu = $('#thuongHieuSearch').val();
        filterProducts(idDanhMuc, idKichThuoc, idMauSac, idThuongHieu, 0);
        isFillter = true;
    });
    //search trong modal
    $('#formSearch').submit(function (e) {
        e.preventDefault();
        var keyword = $('#keyword').val();
        searchProducts(keyword, 0);
        isSearching = true;
    });


    $(document).on('click', '.page-link', function (event) {
        event.preventDefault();
        var page = $(this).data('page');
        if (isSearching) {
            searchProducts($('#keyword').val(), page);
        } else if (isFillter) {
            filterProducts($('#danhMucSearch').val(), $('#kichThuocSearch').val(), encodeURIComponent($('#mauSacSearch').val()), $('#thuongHieuSearch').val(), page);
        } else {
            fetchProducts(page);
        }


    });

    var spctId='';
    $(document).on('click', '.aBtn', function () {
        spctId = $(this).data('id');
        console.log(spctId);
        $.get('/giao_dich/viewOne/?id=' + spctId, function (spct) {
            $('#formAddGH #maSPCT').text(spct.ma);
            $('#formAddGH #ktSPCT').text(spct.kichThuoc.ten);
            $('#formAddGH #msSPCT').text(' ').css({'background-color': spct.mauSac.ten,'min-height':'10px','min-width':'50px'});
            $('#formAddGH #idSPCT').val(spct.id);
            $('#formAddGH #slGH').val(1);
            $('#spct').modal('hide');
            $('#addGH').modal('show');
            modalHide();

        });

    });
    $('#mauSacSearch').change(updateSelectBackgroundColor);
    $(document).on('change', '.slGH', function () {
        console.log('ok:'+spctId);
        var inputValue = parseFloat($(this).val());
        $.get('/giao_dich/viewOne/?id=' + spctId, function (spct) {
            var maxValue = parseFloat(spct.sl);
            console.log("slM:"+maxValue);
            console.log("sl:"+inputValue);

            var newValue;
            if (isNaN(inputValue) || inputValue < 0) {
                newValue = 1;
            } else if (inputValue > maxValue) {
                newValue = maxValue;
            } else {
                newValue = inputValue; // Giữ nguyên giá trị nếu nó hợp lệ
            }

            // Gán giá trị mới cho ô input
            $(this).val(newValue);
            console.log("sl:"+newValue);
        }.bind(this)); // Chuyển context của function bên trong $.get sang context của sự kiện change

    });


}
function updateSelectBackgroundColor() {
    var selectedOption = $('#mauSacSearch option:selected');
    var selectedColor = selectedOption.css('background-color');
    $('#mauSacSearch').css('background-color', selectedColor);
}
function modalHide() {
    $(document).on('click', '.cBtn', function () {
        $('#spct').modal('show');
        $('#addGH').modal('hide');// Hiển thị lại modal đầu tiên khi modal thứ hai ẩn
    });

}

//hàm filter
function filterProducts(danhMucId, kichThuocId, mauSacId, thuongHieuId, page = 0) {


    var url = '/giao_dich/filter?danhMucSearch=' + danhMucId +
        '&kichThuocSearch=' + kichThuocId +
        '&mauSacSearch=' + mauSacId +
        '&thuongHieuSearch=' + thuongHieuId +
        '&p=' + page;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            if (data) {
                $('#table1 tbody').empty();
                $.each(data.content, function (index, spct) {
                    var giaSPCT = formatCurrency(spct.gia);
                    $('#table1 tbody').append(`
   
                                                <tr>
                                                   
                                                    <td>${index + 1}</td>
                                                    <td >${spct.ma}</td>
                                                    <td >${spct.sanPham.danhMuc.ten}</td>
                                                    <td >${spct.sanPham.thuongHieu.ten}</td>
                                                    <td>${spct.kichThuoc.ten}</td>
                                                    <td>
                                                        <div class="badge" style="background-color:${spct.mauSac.ten};min-height:10px;min-width:50px"> </div>
                                                    </td>
                                                    <td >${spct.sl}</td>
                                                    <td >${giaSPCT}</td>
                                                    <td><a class="btn btn-outline-warning aBtn" data-bs-toggle="modal" data-bs-target="#addGH" data-id="${spct.id}"><i data-feather="shopping-cart"></i></a></td>
                                                </tr>

                    `);
                });
                updatePagination(data);
            } else {
                Swal.fire({
                    title: "Không tìm thấy sản phẩm ",
                    icon: "info",


                });
            }

            feather.replace();
        },

    });
}


//hàm search trong modal
function searchProducts(keyword, page = 0) {

    var url = '/giao_dich/search?keyword=' + keyword + '&p=' + page;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            if (data) {
                $('#table1 tbody').empty();
                // Add new data
                $.each(data.content, function (index, spct) {
                    var giaSPCT = formatCurrency(spct.gia);
                    $('#table1 tbody').append(`
   
                                                <tr>
                                                    <td>${index + 1}</td>
                                                    <td >${spct.ma}</td>
                                                    <td >${spct.sanPham.danhMuc.ten}</td>
                                                    <td >${spct.sanPham.thuongHieu.ten}</td>
                                                    <td>${spct.kichThuoc.ten}</td>
                                                    <td>
                                                        <div class="badge" style="background-color:${spct.mauSac.ten};min-height:10px;min-width:50px"> </div>
                                                    </td>
                                                    <td >${spct.sl}</td>
                                                    <td >${giaSPCT}</td>
                                                    <td><a class="btn btn-outline-warning aBtn" data-bs-toggle="modal" data-bs-target="#addGH" data-id="${spct.id}"><i data-feather="shopping-cart"></i></a></td>

                                                </tr>

                    `);
                });
                updatePagination(data);
            } else {
                Swal.fire({
                    title: "Không tìm thấy sản phẩm nào có mã " + keyword,
                    icon: "info",
                });
            }

            feather.replace();
        }
    });

}

//hàm fillTableSPCT
function fetchProducts(page = 0) {

    var url = '/giao_dich/spct?p=' + page;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            // Clear previous data
            $('#table1 tbody').empty();
            // Add new data
            $.each(data.content, function (index, spct) {
                var giaSPCT = formatCurrency(spct.gia);
                $('#table1 tbody').append(`
   
                                                <tr>
                                                    <td>${index + 1}</td>
                                                    <td >${spct.ma}</td>
                                                    <td >${spct.sanPham.danhMuc.ten}</td>
                                                    <td >${spct.sanPham.thuongHieu.ten}</td>
                                                    <td>${spct.kichThuoc.ten}</td>
                                                    <td>
                                                        <div class="badge" style="background-color:${spct.mauSac.ten};min-height:10px;min-width:50px"> </div>
                                                    </td>
                                                    <td >${spct.sl}</td>
                                                    <td >${giaSPCT}</td>
                                                    <td><a class="btn btn-outline-warning aBtn" data-bs-toggle="modal" data-bs-target="#addGH" data-id="${spct.id}"><i data-feather="shopping-cart"></i></a></td>

                                                </tr>

                    `);
            });
            updatePagination(data);
            feather.replace();
        }
    });
}

//hàm phân trang
function updatePagination(data) {
    var totalPages = data.totalPages;
    var currentPage = data.number + 1; // Trang hiện tại (đánh số từ 1)

    $('#page').empty();

    // Chỉ hiển thị nút "Previous" nếu không phải là trang đầu tiên
    if (currentPage > 1) {
        var prevPageLink = $('<a>', {
            class: 'page-link',
            href: '#',
            'data-page': currentPage - 2 // Giảm 2 để lấy trang trước đó
        }).text('Previous');
        var prevPageItem = $('<li>', {
            class: 'page-item'
        }).append(prevPageLink);
        $('#page').append(prevPageItem);
    }

    // Hiển thị số trang từ trang hiện tại - 2 đến trang hiện tại + 2
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
function messegesCancel() {
    $("#btnCancelTL").click(function (e) {


        if (!validateMoTaCancel() ) {

            e.preventDefault();
            return;
        }
        e.preventDefault();


        Swal.fire({
            title: "Are you sure?",
            text: "Bạn có chắc xác nhận hủy đơn hàng này?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                $("#formCancelTL").submit();
                Swal.fire({
                    position: "top-end",
                    icon: "success",
                    title: "Xác nhận hủy đơn hàng thành công",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });

    });

}
function messegesXN() {
    $("#btnXNTL").click(function (e) {


        if (!validateMoTa()) {

            e.preventDefault();
            return;
        }
        e.preventDefault();

        Swal.fire({
            title: "Are you sure?",
            text: "Bạn có đồng ý xác nhận không?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                $("#formXNTL").submit();
                Swal.fire({
                    position: "top-end",
                    icon: "success",
                    title: "Xác nhận đơn hàng thành công",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });

    });

}
function messegesXNShip() {
    $("#btnXNShipTL").click(function (e) {


        if (!validateGiaShip() || !validateMoTaXNShip()) {

            e.preventDefault();
            return;
        }
        e.preventDefault();

        Swal.fire({
            title: "Are you sure?",
            text: "Bạn có đồng ý xác nhận không?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                $("#formXNShipTL").submit();
                Swal.fire({
                    position: "top-end",
                    icon: "success",
                    title: "Xác nhận đơn hàng thành công",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });

    });

}
function messegesXoaSPCT() {
    $("#btnXoaSPCT").click(function (e) {

        e.preventDefault();

        Swal.fire({
            title: "Are you sure?",
            text: "Bạn có chắc muốn xóa không?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href=$("#btnXoaSPCT").attr("href");
                Swal.fire({
                    position: "top-end",
                    icon: "success",
                    title: "Xóa thành công",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });

    });

}
function messegesSuaSPCT() {
    $("#btnSuaSPCT").click(function (e) {

        e.preventDefault();


        Swal.fire({
            title: "Are you sure?",
            text: "Bạn có chắc muốn sửa không?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                $("#formSuaSPCT").submit();
                Swal.fire({
                    position: "top-end",
                    icon: "success",
                    title: "Sửa thành công",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });

    });

}
function messegesAddGH() {
    $("#btnAddGH").click(function (e) {

        e.preventDefault();


        Swal.fire({
            title: "Are you sure?",
            text: "Bạn có chắc muốn thêm sản phẩm này vào đơn hàng không?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                $("#formAddGH").submit();
                Swal.fire({
                    position: "top-end",
                    icon: "success",
                    title: "Thêm thành công",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        });

    });

}

function validateGiaShip() {
    var sl = document.querySelector("#giaShip").value;
    if (sl.trim() === "" || isNaN(sl)) {
        Swal.fire({
            title: "Error!",
            text: "Vui lòng nhập giá ship hợp lệ ",
            icon: "warning",

        });
        return false;
    }
    if (sl <= 0) {
        Swal.fire({
            title: "Error!",
            text: "Giá ship phải lớn hơn 0 ",
            icon: "warning",

        });
        return false;
    }

    return true;
}
function validateMoTa() {
    var sl = document.querySelector("#moTaXN").value;
    if (sl.trim() === "" ) {
        Swal.fire({
            title: "Error!",
            text: "Mô tả không được để trống",
            icon: "warning",

        });
        return false;
    }


    return true;
}
function validateMoTaXNShip() {
    var sl = document.querySelector("#moTaXNShip").value;
    if (sl.trim() === "" ) {
        Swal.fire({
            title: "Error!",
            text: "Mô tả không được để trống",
            icon: "warning",

        });
        return false;
    }


    return true;
}
function validateMoTaCancel() {
    var sl = document.querySelector("#moTaCancel").value;
    if (sl.trim() === "" ) {
        Swal.fire({
            title: "Error!",
            text: "Mô tả không được để trống",
            icon: "warning",

        });
        return false;
    }


    return true;
}
function checkInput(input) {
    var maxValue = parseFloat(input.getAttribute('max'));
    var value = parseFloat(input.value);

    // Kiểm tra nếu giá trị nhập không phải là số hoặc là số âm
    if (isNaN(value) || value < 0) {
        // Nếu không phải số hoặc số âm, đặt giá trị của input là giá trị cũ
        input.value = input.getAttribute('value');
    } else if (value > maxValue) {
        // Nếu giá trị nhập vượt quá giá trị max, đặt giá trị của input là giá trị max
        input.value = maxValue;
    }
}









