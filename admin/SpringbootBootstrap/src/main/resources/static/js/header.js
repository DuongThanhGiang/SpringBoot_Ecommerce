$(document).ready(function () {
    console.log('header');
    findAllGH(0);
    getSLAndDGGHCT();

    $(document).on('click', '.page-SPCT', function (event) {
        event.preventDefault();
        var page = $(this).data('page');
        findAllGH(page);


    });
});

function formatCurrency(value) {
    var number = Number(value);
    var formattedNumber = number.toLocaleString('vi-VN', { minimumFractionDigits: 0, maximumFractionDigits: 0 });
    return formattedNumber + ' VNĐ';
}

function getSLAndDGGHCT() {
    var tongTien = 0;
    var soLuongSP = 0;
    $.ajax({
        type: 'GET',
        url: '/shop/getAllGHCT',
        success: function (dt) {

            $.each(dt, function (index, ghct1) {
                tongTien = formatCurrency(ghct1.gioHang.thanhTien);
                console.log("tt:" + tongTien)

            });
            soLuongSP += dt.length;
            $('.tongTiendr').text(tongTien);
            $('#slSPCT').text(soLuongSP);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

function findAllGH(page) {

    $.ajax({
        type: 'GET',
        url: '/shop/findAllGHCT?p=' + page,
        success: function (dtGHCT) {


            $('#rdGHCT').empty();
            $.each(dtGHCT.content, function (index, ghct) {
                var giaSPCT = formatCurrency(ghct.donGia);
                $('#rdGHCT').append(`
                 <div class="product">
                                <div class="product-cart-details">
                                    <h4 class="product-title">
                                        <a href="/spOnl/detailSP/${ghct.sanPhamCT.sanPham.id}">${ghct.sanPhamCT.sanPham.ten}</a>
                                    </h4>
                                        <div >
                                                Màu:<span class="badge"  style="background-color:${ghct.sanPhamCT.mauSac.ten};min-height: 10px;min-width: 50px" > </span>
                                        </div>
                                        <div >
                                               <span class="cart-product-qty">Size:${ghct.sanPhamCT.kichThuoc.ten} - SL:${ghct.soLuong}</span>
                                        </div>

                                    <div >
                                                 Giá:<span class="cart-product-qty" style="color: red">${giaSPCT}</span>
                                                
                                    </div>
                                </div>

                                <figure  class="product-image-container">
                                 <a href="/shop/detailSP/${ghct.sanPhamCT.sanPham.id}" class="product-image">
                                        <img id="imgSPCT${ghct.idGhct}" src="" alt="product">
                                    </a>
                                
                                </figure>
                               
                 </div>
                 `)
                $.ajax({
                    type: 'GET',
                    url: '/spOnl/anh?id=' + ghct.sanPhamCT.sanPham.id,
                    success: function (imgSP) {
                        $.ajax({
                            type: 'GET',
                            url: '/spOnl/convertToBase64?id=' + imgSP[0].id,
                            success: function (response) {
                                $('#imgSPCT' + ghct.idGhct).attr('src', 'data:image/jpeg;base64,' + response);

                            },
                            error: function (xhr, status, error) {
                                console.error('Error:', error);
                            },

                        });
                    },
                    error: function (xhr, status, error) {
                        console.error('Error:', error);
                    }

                });


            });
            updatePagination(dtGHCT)
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

function updatePagination(data) {
    var totalPages = data.totalPages;
    var currentPage = data.number + 1; // Trang hiện tại (đánh số từ 1)

    $('#pageGHCT').empty();

    // Chỉ hiển thị nút "Previous" nếu không phải là trang đầu tiên
    if (currentPage > 1) {
        var prevPageLink = $('<a>', {
            class: 'page-link page-SPCT',
            href: '#',
            'data-page': currentPage - 2 // Giảm 2 để lấy trang trước đó
        }).text('Previous');
        var prevPageItem = $('<li>', {
            class: 'page-item'
        }).append(prevPageLink);
        $('#pageGHCT').append(prevPageItem);
    }

    // Hiển thị số trang từ trang hiện tại - 2 đến trang hiện tại + 2
    var startPage = Math.max(1, currentPage - 1);
    var endPage = Math.min(totalPages, currentPage + 1);

    for (var i = startPage; i <= endPage; i++) {
        var pageLink = $('<a>', {
            class: 'page-link page-SPCT',
            href: '#',
            'data-page': i - 1
        }).text(i);
        var listItem = $('<li>', {
            class: 'page-item ' + (i === currentPage ? 'active' : '')
        }).append(pageLink);
        $('#pageGHCT').append(listItem);
    }

    // Chỉ hiển thị nút "Next" nếu không phải là trang cuối cùng
    if (currentPage < totalPages) {
        var nextPageLink = $('<a>', {
            class: 'page-link page-SPCT',
            href: '#',
            'data-page': currentPage
        }).text('Next');
        var nextPageItem = $('<li>', {
            class: 'page-item'
        }).append(nextPageLink);
        $('#pageGHCT').append(nextPageItem);
    }

}
