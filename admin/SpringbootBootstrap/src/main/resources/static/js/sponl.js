$(document).ready(function () {
    var listDM = [];
    var listTH = [];
    var listMS = [];
    var listKT = [];
    var isFilterDM = false;
    var isFilterKT = false;
    var isFilterMS = false;
    var isFilterTH = false;
    finAllSP(0);

    $('.cbDM').change(function () {
        listDM = [];
        $('.cbDM:checked').each(function () {
            listDM.push($(this).val());
        });

        console.log('listDM:' + listDM);
    });
    $('.cbTH').change(function () {
        listTH = [];
        $('.cbTH:checked').each(function () {
            listTH.push($(this).val());
        });

        console.log('listTH:' + listTH);
    });
    $('.cbKT').change(function () {
        listKT = [];
        $('.cbKT:checked').each(function () {
            listKT.push($(this).val());
        });

        console.log('listKT:' + listKT);
    });

    $('.cbMS').change(function () {
        listMS = [];
        $('.cbMS:checked').each(function () {
            listMS.push($(this).val());
        });

        console.log('listMS:' + listMS);
    });
    $(document).on('submit', '#formFtDM', function (e) {
        e.preventDefault();
        filterDM(listDM, 0);
        isFilterDM = true;
        isFilterMS=false;
        isFilterTH=false;
        isFilterKT=false

    });
    $(document).on('submit', '#formFtTH', function (e) {
        e.preventDefault();
        filterTH(listTH, 0);
        isFilterTH = true;
        isFilterDM = false;
        isFilterMS=false;
        isFilterKT=false;

    });
    $(document).on('submit', '#formFtKT', function (e) {
        e.preventDefault();
        filterKT(listKT, 0);
        isFilterKT = true;
        isFilterDM = false;
        isFilterMS=false;
        isFilterTH=false;

    });
    $(document).on('submit', '#formFtMS', function (e) {
        e.preventDefault();
        filterMS(listMS, 0);
        isFilterMS = true;
        isFilterDM = false;
        isFilterKT=false;
        isFilterTH=false;

    });
    $(document).on('click', '.pageSPOnl', function (event) {
        event.preventDefault();
        var page = $(this).data('page');
        if (isFilterDM) {
            filterDM(listDM, page);
        } else if (isFilterTH) {
            filterTH(listTH, page);
        } else if (isFilterKT){
           filterKT(listKT, page);
        } else if (isFilterMS){
            filterMS(listMS, page);
        } else{
            finAllSP(page);
        }


    });
});
function fillSP(data) {
    if (data) {
        $('#ren-derSP').empty();


        $.each(data.content, function (index, sp) {
            var maSP = sp.ma;
            var idImg = 'imgSPCT' + maSP;

            var minPrice = 0;
            var maxPrice = 0;
            $.ajax({
                type: 'GET',
                url: '/spOnl/gia_min?id=' + sp.id,
                success: function (dgmin) {
                    minPrice = formatCurrency(dgmin);
                }
            });
            $.ajax({
                type: 'GET',
                url: '/spOnl/gia_max?id=' + sp.id,
                success: function (dgmax) {
                    maxPrice = formatCurrency(dgmax);
                }
            });
            $.ajax({
                type: 'GET',
                url: '/spOnl/anh?id=' + sp.id,
                success: function (spctdata) {

                    $.ajax({
                        type: 'GET',
                        url: '/spOnl/convertToBase64?id=' + spctdata[0].id,
                        success: function (response) {
                            $('#imgSPCT' + maSP + '').attr('src', 'data:image/jpeg;base64,' + response)


                        },
                        error: function (xhr, status, error) {
                            console.error('Error:', error);
                        }

                    });

                    $('#ren-derSP').append(`
    <div class="col-6 col-md-4 col-lg-4">
        <div class="product product-7 text-center">
            <figure class="product-media">
                <a href="/spOnl/detailSP/${sp.id}">
                    <img style="max-width: 315px;min-width: 315px;max-height: 315px;min-height: 315px;" id="${idImg}" src="" alt="Product image"
                         class="product-image">
                </a>
        <div class="product-action">
                                    <a href="/spOnl/detailSP/${sp.id}" class="btn-product btn-cart">
                                    <span>Xem chi tiết</span></a>
                                </div>
            </figure>

            <div class="product-body">
                <h3 class="product-title"><a style="text-decoration: none" href="/spOnl/detailSP/${sp.id}">${sp.ten}</a></h3>
                <div class="product-price">
                 ${minPrice} - ${maxPrice}
                </div>
            </div>
        </div>
    </div>

                    `);


                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }

            });


        });
        updatePaginationSPOnl(data);
    } else {
        Swal.fire({
            title: "Không tìm thấy sản phẩm ",
            icon: "info",


        });
    }

}

function finAllSP(page) {
    console.log("page"+page)
    var url = '/spOnl/all?p=' + page;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
           fillSP(data);

        },

    });


}

function filterDM(listDM, page) {
    var url = '/spOnl/filterByDM?listDM=' + listDM + '&p=' + page;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            fillSP(data);


        },

    });


}

function filterTH(listTH, page) {
    var url = '/spOnl/filterByTH?listTH=' + listTH + '&p=' + page;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            fillSP(data);


        },

    });


}
function formatCurrency(value) {
    var number = Number(value);
    var formattedNumber = number.toLocaleString('vi-VN', { minimumFractionDigits: 0, maximumFractionDigits: 0 });
    return formattedNumber + ' VNĐ';
}
function filterKT(listKT, page) {


    var url = '/spOnl/filterByKT?listKT=' + listKT + '&p=' + page;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            fillSP(data);


        },

    });


}

function filterMS(listMS, page) {
    var url = '/spOnl/filterByMS?listMS=' + listMS + '&p=' + page;
    $.ajax({
        type: 'GET',
        url: url,
        success: function (data) {
            fillSP(data);


        },

    });


}

function updatePaginationSPOnl(data) {
    var totalPages = data.totalPages;
    var currentPage = data.number + 1;

    $('#page').empty();


    if (currentPage > 1) {
        var prevPageLink = $('<a>', {
            class: 'page-link pageSPOnl',
            href: '#',
            'data-page': currentPage - 2
        }).text('Previous');
        var prevPageItem = $('<li>', {
            class: 'page-item'
        }).append(prevPageLink);
        $('#page').append(prevPageItem);
    }
    var startPage = Math.max(1, currentPage - 1);
    var endPage = Math.min(totalPages, currentPage + 1);

    for (var i = startPage; i <= endPage; i++) {
        var pageLink = $('<a>', {
            class: 'page-link pageSPOnl',
            href: '#',
            'data-page': i - 1
        }).text(i);
        var listItem = $('<li>', {
            class: 'page-item ' + (i === currentPage ? 'active' : '')
        }).append(pageLink);
        $('#page').append(listItem);
    }


    if (currentPage < totalPages) {
        var nextPageLink = $('<a>', {
            class: 'page-link pageSPOnl',
            href: '#',
            'data-page': currentPage
        }).text('Next');
        var nextPageItem = $('<li>', {
            class: 'page-item'
        }).append(nextPageLink);
        $('#page').append(nextPageItem);
    }

}

