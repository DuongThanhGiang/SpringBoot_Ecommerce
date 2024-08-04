Create database noble_loafers
go
use noble_loafers
go
create table kich_thuoc
(
    id_kich_thuoc uniqueidentifier DEFAULT NEWID() primary key,
    ma            varchar(10),
    ten           nvarchar(20),
    trang_thai    int,
    tao_luc       date,
    sua_luc       date,
    tao_boi       nvarchar(20),
    sua_boi       nvarchar(20),

)


create table mau_sac
(
    id_mau_sac uniqueidentifier DEFAULT NEWID() primary key,
    ma         varchar(10),
    ten        nvarchar(20),
    trang_thai int,
    tao_luc    date,
    sua_luc    date,
    tao_boi    nvarchar(20),
    sua_boi    nvarchar(20),

)

create table thuong_hieu
(
    id_thuong_hieu uniqueidentifier DEFAULT NEWID() primary key,
    ma             varchar(10),
    ten            nvarchar(20),
    trang_thai     int,
    tao_luc        date,
    sua_luc        date,
    tao_boi        nvarchar(20),
    sua_boi        nvarchar(20),

)

create table danh_muc
(
    id_danh_muc uniqueidentifier DEFAULT NEWID() primary key,
    ma          varchar(10),
    ten         nvarchar(20),
    trang_thai  int,
    tao_luc     date,
    sua_luc     date,
    tao_boi     nvarchar(20),
    sua_boi     nvarchar(20),

)

create table san_pham
(
    id_san_pham    uniqueidentifier DEFAULT NEWID() primary key,
    id_danh_muc    uniqueidentifier,
    foreign key (id_danh_muc) references danh_muc (id_danh_muc),
    id_thuong_hieu uniqueidentifier,
    foreign key (id_thuong_hieu) references thuong_hieu (id_thuong_hieu),
    ma             varchar(10),
    ten            nvarchar(20),
    trang_thai     int,
    tao_luc        date,
    sua_luc        date,
    tao_boi        nvarchar(20),
    sua_boi        nvarchar(20),

)
create table san_pham_chi_tiet
(
    id_spct       uniqueidentifier DEFAULT NEWID() primary key,
    id_kich_thuoc uniqueidentifier,
    foreign key (id_kich_thuoc) references kich_thuoc (id_kich_thuoc),
    id_mau_sac    uniqueidentifier,
    foreign key (id_mau_sac) references mau_sac (id_mau_sac),
    id_san_pham   uniqueidentifier,
    foreign key (id_san_pham) references san_pham (id_san_pham),
    ma            varchar(20),
    mo_ta         nvarchar(100),
    so_luong_ton  int,
    gia_ban       decimal(20),
    anh           image,
    trang_thai    int,
    tao_luc       date,
    sua_luc       date,
    tao_boi       nvarchar(20),
    sua_boi       nvarchar(20),

)
create table Anh
(
     id_hinh_anh    uniqueidentifier DEFAULT NEWID() primary key,
     id_san_pham uniqueidentifier,
     foreign key (id_san_pham) references san_pham (id_san_pham),
     duong_dan    image,
)

create table _role
(
    id_role uniqueidentifier DEFAULT NEWID() primary key,
    ten        nvarchar(50),
    tao_luc    date,
    sua_luc    date,
    tao_boi    nvarchar(20),
    sua_boi    nvarchar(20),

)
create table nhan_vien
(
    id_nhan_vien  uniqueidentifier DEFAULT NEWID() primary key,
    id_role    uniqueidentifier,
    foreign key (id_role) references _role(id_role),
    email         varchar(50),
    ten           nvarchar(50),
    gioi_tinh     int,
    ngay_sinh     date,
    dia_chi       nvarchar(100),
    sdt           varchar(20),
    mat_khau      varchar(MAX),
    trang_thai    int,
    tao_luc       date,
    sua_luc       date,
    tao_boi       nvarchar(20),
    sua_boi       nvarchar(20),
)

create table khach_hang
(
    id_kh         uniqueidentifier DEFAULT NEWID() primary key,
    ten           nvarchar(50),
    ngay_sinh     date,
    gioi_tinh     int,
    email         varchar(50),
    sdt           varchar(10),
    dia_chi       nvarchar(100),
    mat_khau      varchar(MAX),
    trang_thai    int,
    tao_luc       date,
    sua_luc       date,
    tao_boi       nvarchar(20),
    sua_boi       nvarchar(20),

)
create table gio_hang
(
    id_gio_hang uniqueidentifier DEFAULT NEWID() primary key,
    id_kh       uniqueidentifier,
    foreign key (id_kh) references khach_hang (id_kh),
    ma          varchar(20),
    thanh_tien  decimal(20),
    trang_thai  int,
    tao_luc     date,
    sua_luc     date,
    tao_boi     nvarchar(20),
    sua_boi     nvarchar(20),

)
create table gio_hang_chi_tiet
(
    id_ghct     uniqueidentifier DEFAULT NEWID() primary key,
    id_spct     uniqueidentifier,
    foreign key (id_spct) references san_pham_chi_tiet (id_spct),
    id_gio_hang uniqueidentifier,
    foreign key (id_gio_hang) references gio_hang (id_gio_hang),
    so_luong    int,
    trang_thai  int,
    don_gia     decimal(20),
    tao_luc     date,
    sua_luc     date,
    tao_boi     nvarchar(20),
    sua_boi     nvarchar(20),

)

create table phieu_giam_gia
(
    id_pgg              uniqueidentifier DEFAULT NEWID() primary key,
    ma                  varchar(20),
    ten                 nvarchar(20),
    don_vi              int,
    gia_tri_giam        float,
    gia_tri_toi_thieu   float,
    gia_tri_giam_toi_da float,
    ngay_bat_dau        date,
    ngay_ket_thuc       date,
    mo_ta               nvarchar(50),
    so_luong            int,
    trang_thai          int,
    tao_luc             date,
    sua_luc             date,
    tao_boi             nvarchar(20),
    sua_boi             nvarchar(20),
)

create table hoa_don
(
    id_hoa_don      uniqueidentifier DEFAULT NEWID() primary key,
    id_kh           uniqueidentifier,
    foreign key (id_kh) references khach_hang (id_kh),
    id_pgg          uniqueidentifier,
    foreign key (id_pgg) references phieu_giam_gia (id_pgg),
    id_nv           uniqueidentifier,
    foreign key (id_nv) references nhan_vien (id_nhan_vien),
    ma              varchar(20),
    gia             decimal(20),
    ngay_thanh_toan date,
    ngay_ship       date,
    ngay_nhan       date,
    tinh_trang      int,
    dia_chi         nvarchar(50),
    tien_ship       decimal(20),
    thanh_tien      decimal(20),
    hinh_thuc       int,
    ghi_chu         nvarchar(max),
    quan_huyen      nvarchar(50),
    thanh_pho	    nvarchar(50),
    phuong_xa       nvarchar(50),
    tao_luc         date,
    sua_luc         date,
    tao_boi         nvarchar(20),
    sua_boi         nvarchar(20),

)

create table hoa_don_chi_tiet
(
    id_hoa_don_chi_tiet uniqueidentifier DEFAULT NEWID() primary key,
    id_spct             uniqueidentifier,
    foreign key (id_spct) references san_pham_chi_tiet (id_spct),
    id_hoa_don          uniqueidentifier,
    foreign key (id_hoa_don) references hoa_don (id_hoa_don),
    gia                 decimal(20),
    so_luong            int,
    trang_thai          int,
    tao_luc             date,
    sua_luc             date,
    tao_boi             nvarchar(20),
    sua_boi             nvarchar(20),
)
create table lich_su_hoa_don
(
    id    uniqueidentifier DEFAULT NEWID() primary key,
    id_hoa_don    uniqueidentifier,
    foreign key (id_hoa_don) references hoa_don (id_hoa_don),
    ngay_tao       datetime,
    trang_thai     int,
    nguoi_tao      varchar(20),
    mo_ta          nvarchar(max),

)

create table khuyen_mai
(
    id_km         uniqueidentifier DEFAULT NEWID() primary key,
    ma            varchar(20),
    ten           nvarchar(20),
    don_vi        int,
    gia_tri_giam  float,
    ngay_bat_dau  date,
    ngay_ket_thuc date,
    mo_ta         nvarchar(50),
    so_luong      int,
    trang_thai    int,
    tao_luc       date,
    sua_luc       date,
    tao_boi       nvarchar(20),
    sua_boi       nvarchar(20),

)

create table khuyen_mai_chi_tiet
(
    id_kmct    uniqueidentifier DEFAULT NEWID() primary key,
    id_sp      uniqueidentifier,
    foreign key (id_sp) references san_pham (id_san_pham),
    id_km      uniqueidentifier,
    foreign key (id_km) references khuyen_mai (id_km),
    trang_thai int,
    tao_luc    date,
    sua_luc    date,
    tao_boi    nvarchar(20),
    sua_boi    nvarchar(20),

)

CREATE TRIGGER trg_generate_ma_hoa_don
    ON hoa_don
    FOR INSERT
    AS
BEGIN
    DECLARE @id_hoa_don uniqueidentifier;
    DECLARE @ma_hoa_don VARCHAR(20);

    SELECT @id_hoa_don = id_hoa_don FROM inserted;
    SET @ma_hoa_don = 'HD' + RIGHT(CONVERT(VARCHAR(36), @id_hoa_don), 6);

    UPDATE hoa_don SET ma = @ma_hoa_don WHERE id_hoa_don = @id_hoa_don;
END;

    CREATE TRIGGER trg_generate_ma_sp
        ON san_pham
        FOR INSERT
        AS
    BEGIN
        DECLARE @id_sp uniqueidentifier;
        DECLARE @ma_sp VARCHAR(20);

        SELECT @id_sp = id_san_pham FROM inserted;
        SET @ma_sp = 'SP' + RIGHT(CONVERT(VARCHAR(36), @id_sp), 5);

        UPDATE san_pham SET ma = @ma_sp WHERE id_san_pham = @id_sp;
    END;

        CREATE TRIGGER trg_generate_ma_spct
            ON san_pham_chi_tiet
            FOR INSERT
            AS
        BEGIN
            DECLARE @id_spct uniqueidentifier;
            DECLARE @ma_spct VARCHAR(20);

            SELECT @id_spct = id_spct FROM inserted;
            SET @ma_spct = 'SPCT' + RIGHT(CONVERT(VARCHAR(36), @id_spct), 5);

            UPDATE san_pham_chi_tiet SET ma = @ma_spct WHERE id_spct = @id_spct;
        END;

            CREATE TRIGGER update_status_phieu_giam_gia
                ON phieu_giam_gia
                AFTER INSERT, UPDATE
                AS
            BEGIN
                UPDATE phieu_giam_gia
                SET trang_thai =
                        CASE
                            WHEN phieu_giam_gia.so_luong <= 0 And phieu_giam_gia.trang_thai != 4 THEN 3
                            WHEN phieu_giam_gia.ngay_ket_thuc <= CAST(GETDATE() AS DATE) And phieu_giam_gia.trang_thai != 4 THEN 2
                            WHEN phieu_giam_gia.so_luong > 0 AND phieu_giam_gia.ngay_ket_thuc > CAST(GETDATE() AS DATE) And phieu_giam_gia.trang_thai != 4	 THEN 1
                            ELSE phieu_giam_gia.trang_thai
                            END
                FROM inserted
                WHERE phieu_giam_gia.id_pgg = inserted.id_pgg;
            END;

                CREATE OR ALTER TRIGGER Update_ThanhTien
                    ON gio_hang_chi_tiet
                    AFTER INSERT, UPDATE, DELETE
                    AS
                BEGIN
                    -- Cập nhật thanh_tien trong gio_hang
                    UPDATE gio_hang
                    SET thanh_tien = (
                        SELECT SUM(san_pham_chi_tiet.gia_ban * so_luong)
                        FROM gio_hang_chi_tiet Join san_pham_chi_tiet
						on gio_hang_chi_tiet.id_spct = san_pham_chi_tiet.id_spct
                        WHERE id_gio_hang IN (
                            SELECT id_gio_hang FROM inserted
                            UNION
                            SELECT id_gio_hang FROM deleted
                        )
                    )
                    WHERE id_gio_hang IN (
                        SELECT id_gio_hang FROM inserted
                        UNION
                        SELECT id_gio_hang FROM deleted
                    )
                End

            insert into phieu_giam_gia(ma, ten, don_vi, gia_tri_giam, gia_tri_toi_thieu, gia_tri_giam_toi_da,
                                       ngay_bat_dau, ngay_ket_thuc, mo_ta, so_luong, trang_thai)
            values ('PGG001', N'Phiếu Giảm Giá 1', 1, 10, 100000, 20000, '2024/1/1', '2024/12/12', N'Mô tả 1', 50, 1),
                   ('PGG002', N'Phiếu Giảm Giá 2', 2, 10000, 50000, 10000, '2024/1/1', '2024/12/12', N'Mô tả 2', 50, 1),
                   ('PGG000', 'Voucher Default', 1, 0, 0, 0, '2024/1/1', '2024/12/12', N'', 9999, 1)
            insert into khach_hang(ten,sdt,trang_thai) values
            (N'Nguyễn Văn Hoàng','0555555555',1)
