//fill table SanPham , phân trang, tìm kiếm
$(document).ready(function () {
    var firstTab = $('button[data-bs-toggle="tab"].active');
    var maHD = firstTab.data('hd-ma');
    spctAll(maHD)

    $('#mauSacSearch'+maHD).on('change',function () {
        updateSelectBackgroundColor(maHD);
    });
    $('button[data-bs-toggle="tab"]').on('click', function () {
        maHD = $(this).data('hd-ma');
        console.log(maHD);
        spctAll(maHD);

    });

    createChart("myChart1", "http://localhost:8080/home/x", "http://localhost:8080/home/y-so-don-hang", "Biểu đồ Số đơn hàng");
    createChart("myChart2", "http://localhost:8080/home/x", "http://localhost:8080/home/y-so-san-pham", "Biểu đồ Số sản phẩm đã bán");
    createChart("myChart3", "http://localhost:8080/home/x", "http://localhost:8080/home/y-doanh-thu", "Biểu đồ Doanh Thu");
});

function updateSelectBackgroundColor(maHD) {
    var selectedOption = $('#mauSacSearch'+maHD+' option:selected');
    var selectedColor = selectedOption.css('background-color');
    $('#mauSacSearch'+maHD).css('background-color', selectedColor);
}
function formatCurrency(value) {
    var number = Number(value);
    var formattedNumber = number.toLocaleString('vi-VN', { minimumFractionDigits: 0, maximumFractionDigits: 0 });
    return formattedNumber + ' VNĐ';
}

function createChart(chartId, xApi, yApi, ten) {
    var x = [];
    var y = [];
    const urlParams = new URLSearchParams(window.location.search);
    const thoiGian = urlParams.get('thoiGian');
    let tu = urlParams.get('tuNgay');
    let den = urlParams.get('denNgay');
    if (tu==null){
        tu=" ";
    }
    if (den==null){
        den=" ";
    }
    console.log("tu:"+tu);
    console.log("den"+den);
    fetch(`${xApi}?thoiGian=${thoiGian}&tuNgay=${tu}&denNgay=${den}`)
        .then(response => response.json())
        .then(data => {
            x = data;
            // Gọi hàm createChartWithData() sau khi fetch danh sách
            fetch(`${yApi}?thoiGian=${thoiGian}&tuNgay=${tu}&denNgay=${den}`)
                .then(response => response.json())
                .then(data => {
                    y = data;
                    createChartWithData(chartId, x, y, ten);
                });
        });
}

function createChartWithData(chartId, x, y, ten) {
    new Chart(chartId, {
        type: "bar",
        data: {
            labels: x,
            datasets: [{
                backgroundColor: "red",
                data: y
            }]
        },
        options: {
            title: {
                display: true,
                text: ten
            },
            legend: {display: false},
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                    }
                }]
            }
        }
    });
}

function spctAll(maHD) {
    var isSearching = false;
    var isFillter = false;
    fetchProducts(maHD, 0);
    //filter
    $('#formFilter' + maHD + '').submit(function (e) {
        e.preventDefault();
        var idKichThuoc = $('#kichThuocSearch' + maHD + '').val();
        var idMauSac = encodeURIComponent($('#mauSacSearch' + maHD + '').val());
        var idDanhMuc = $('#danhMucSearch' + maHD + '').val();
        var idThuongHieu = $('#thuongHieuSearch' + maHD + '').val();
        filterProducts(maHD, idDanhMuc, idKichThuoc, idMauSac, idThuongHieu, 0);
        isFillter = true;
    });
    //search trong modal
    $('#formSearch' + maHD + '').submit(function (e) {
        e.preventDefault();
        var keyword = $('#keyword' + maHD + '').val();
        searchProducts(maHD, keyword, 0);
        isSearching = true;
    });

    //search ngoài modal
    $('#formSearchMaSPCT' + maHD + '').submit(function (e) {
        e.preventDefault();
        var keyword = $('#maSPCTSearch' + maHD + '').val();
        if (keyword.trim() !== '') {
            searchByMaSPCT(maHD, keyword);
        } else {
            Swal.fire({
                title: "Vui lòng nhập dữ liệu tìm kiếm",
                icon: "info",


            });
        }


    });

    $(document).on('click', '.page-link', function (event) {
        event.preventDefault();
        var page = $(this).data('page');
        if (isSearching) {
            searchProducts(maHD, $('#keyword' + maHD + '').val(), page);
        } else if (isFillter) {
            filterProducts(maHD, $('#danhMucSearch' + maHD + '').val(), $('#kichThuocSearch' + maHD + '').val(), encodeURIComponent($('#mauSacSearch' + maHD + '').val()), $('#thuongHieuSearch' + maHD + '').val(), page);
        } else {
            fetchProducts(maHD, page);
        }


    });
    var spctId ='';
    $(document).on('click', '.aBtn', function () {
         spctId = $(this).data('id');
        console.log(spctId);
        $.get('/giao_dich/viewOne/?id=' + spctId, function (spct) {
            $('#formAddGH' + maHD + ' #maSPCT' + maHD + '').text(spct.ma);
            $('#formAddGH' + maHD + ' #ktSPCT' + maHD + '').text(spct.kichThuoc.ten);
            $('#formAddGH' + maHD + ' #msSPCT' + maHD + '').text(' ').css({'background-color': spct.mauSac.ten,'min-height':'10px','min-width':'50px'});
            $('#formAddGH' + maHD + ' #idSPCT' + maHD + '').val(spct.id);
            $('#formAddGH' + maHD + ' #slGH' + maHD + '').val(1);
            $('#spct' + maHD + '').modal('hide');
            $('#addGH' + maHD + '').modal('show');
            modalHide(maHD);

        });

    });
    $(document).on('change', '.slGH'+maHD+'', function () {
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

function modalHide(maHD) {
    $(document).on('click', '.cBtn', function () {
        $('#spct' + maHD + '').modal('show');
        $('#addGH' + maHD + '').modal('hide');// Hiển thị lại modal đầu tiên khi modal thứ hai ẩn
    });

}

//hàm filter
function filterProducts(maHD, danhMucId, kichThuocId, mauSacId, thuongHieuId, page = 0) {


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
                $('#table1' + maHD + ' tbody').empty();
                $.each(data.content, function (index, spct) {
                    var giaSPCT = formatCurrency(spct.gia);
                    $('#table1' + maHD + ' tbody').append(`
   
                                                <tr>
                                                   
                                                    <td>${index + 1}</td>
                                                    <td >${spct.ma}</td>
                                                    <td >${spct.sanPham.danhMuc.ten}</td>
                                                    <td >${spct.sanPham.thuongHieu.ten}</td>
                                                    <td>${spct.kichThuoc.ten}</td>
                                                    <td>
                                                        <div class="badge" style="background-color:${spct.mauSac.ten};min-height: 10px;min-width: 50px"> </div>
                                                    </td>
                                                    <td >${spct.sl}</td>
                                                    <td >${giaSPCT}</td>
                                                    <td><a class="btn btn-outline-warning aBtn" data-bs-toggle="modal" data-bs-target="#addGH${maHD}" data-id="${spct.id}"><i data-feather="shopping-cart"></i></a></td>
                                                </tr>

                    `);
                });
                updatePagination(data, maHD);
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


//hàm search ngoài modal
function searchByMaSPCT(maHD, keyword) {

    var url = '/giao_dich/viewOneByMa?maSPCTSearch=' + keyword;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            if (data) {
                $.get(url, function (spct) {
                    $('#formAddGH' + maHD + ' #maSPCT' + maHD + '').text(spct.ma);
                    $('#formAddGH' + maHD + ' #ktSPCT' + maHD + '').text(spct.kichThuoc.ten);
                    $('#formAddGH' + maHD + ' #msSPCT' + maHD + '').text(' ').css({'background-color': spct.mauSac.ten,'min-height':'10px','min-width':'50px'});
                    $('#formAddGH' + maHD + ' #idSPCT' + maHD + '').val(spct.id);
                    $('#formAddGH' + maHD + ' #slGH' + maHD + '').val(1);
                    $('#addGH' + maHD + '').modal('show');


                });
            } else {
                Swal.fire({
                    title: "Không tìm thấy sản phẩm nào có mã " + keyword,
                    icon: "info",


                });
            }

        }
    });


}

//hàm search trong modal
function searchProducts(maHD, keyword, page = 0) {

    var url = '/giao_dich/search?keyword=' + keyword + '&p=' + page;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            if (data) {
                $('#table1' + maHD + ' tbody').empty();
                // Add new data
                $.each(data.content, function (index, spct) {
                    var giaSPCT = formatCurrency(spct.gia);
                    $('#table1' + maHD + ' tbody').append(`
   
                                                <tr>
                                                    <td>${index + 1}</td>
                                                    <td >${spct.ma}</td>
                                                    <td >${spct.sanPham.danhMuc.ten}</td>
                                                    <td >${spct.sanPham.thuongHieu.ten}</td>
                                                    <td>${spct.kichThuoc.ten}</td>
                                                    <td>
                                                        <div class="badge" style="background-color:${spct.mauSac.ten};min-height: 10px;min-width: 50px"> </div>
                                                    </td>
                                                    <td >${spct.sl}</td>
                                                    <td >${giaSPCT}</td>
                                                    <td><a class="btn btn-outline-warning aBtn" data-bs-toggle="modal" data-bs-target="#addGH${maHD}" data-id="${spct.id}"><i data-feather="shopping-cart"></i></a></td>

                                                </tr>

                    `);
                });
                updatePagination(data, maHD);
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
function fetchProducts(maHD, page = 0) {

    var url = '/giao_dich/spct?p=' + page;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            // Clear previous data
            $('#table1' + maHD + ' tbody').empty();
            // Add new data
            $.each(data.content, function (index, spct) {
                var giaSPCT = formatCurrency(spct.gia);
                $('#table1' + maHD + ' tbody').append(`
   
                                                <tr>
                                                    <td>${index + 1}</td>
                                                    <td >${spct.ma}</td>
                                                    <td >${spct.sanPham.danhMuc.ten}</td>
                                                    <td >${spct.sanPham.thuongHieu.ten}</td>
                                                    <td>${spct.kichThuoc.ten}</td>
                                                    <td>
                                                        <div class="badge" style="background-color:${spct.mauSac.ten};min-height: 10px;min-width: 50px"> </div>
                                                    </td>
                                                    <td >${spct.sl}</td>
                                                    <td >${giaSPCT}</td>
                                                    <td><a class="btn btn-outline-warning aBtn" data-bs-toggle="modal" data-bs-target="#addGH${maHD}" data-id="${spct.id}"><i data-feather="shopping-cart"></i></a></td>

                                                </tr>

                    `);
            });
            updatePagination(data, maHD);
            feather.replace();
        }
    });
}

//hàm phân trang
function updatePagination(data, maHD) {
    var totalPages = data.totalPages;
    var currentPage = data.number + 1; // Trang hiện tại (đánh số từ 1)

    $('#page' + maHD + '').empty();

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
        $('#page' + maHD + '').append(prevPageItem);
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
        $('#page' + maHD + '').append(listItem);
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
        $('#page' + maHD + '').append(nextPageItem);
    }

}


// render tab
$(document).ready(function () {
    //xóa tab
    $('.close-tab').click(function (e) {
        var tabCount = $('#nav-tab button.nav-link').length;
        console.log(tabCount)
        if (tabCount < 2) {
            e.preventDefault();
            Swal.fire("Cần có ít nhất 1 hóa đơn");
            return;
        }
        e.preventDefault();
        showConFirm($('.close-tab').attr("href"));
    });
    $('#add-tab').click(function (e) {
        var tabCount = $('#nav-tab button.nav-link').length;
        console.log(tabCount)
        if (tabCount > 5) {
            e.preventDefault();
            Swal.fire("chỉ có thể tạo tối đa 6 hóa đơn!");
            return;
        }
    });
});

function showConFirm(Url) {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger"
        },
        buttonsStyling: false
    });
    swalWithBootstrapButtons.fire({
        title: "Are you sure?",
        text: "Bạn có chắc muốn xóa hóa đơn này",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes",
        cancelButtonText: "No",
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                position: "top-end",
                icon: "success",
                title: "Xóa hóa đơn thành công",
                showConfirmButton: false,
                timer: 1500
            });
            window.location.href = Url;

        }
    });
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

//fillModalAddGH




