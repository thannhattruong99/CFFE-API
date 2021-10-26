use CFFE_Database;


create table tblCity
(
    CityId   int auto_increment
        primary key,
    CityName varchar(150) not null,
    constraint tblCity_CityName_uindex
        unique (CityName)
);

create table tblDistrict
(
    DistrictId   int auto_increment
        primary key,
    CityId       int          not null,
    DistrictName varchar(150) not null,
    constraint tblDistrict_DistrictName_uindex
        unique (DistrictName),
    constraint FK_41
        foreign key (CityId) references tblCity (CityId)
);

create index fkIdx_42
    on tblDistrict (CityId);

create table tblRole
(
    RoleId   int auto_increment
        primary key,
    RoleName varchar(50) not null,
    constraint tblRole_RoleName_uindex
        unique (RoleName)
);

create table tblStatus
(
    StatusId   int auto_increment
        primary key,
    StatusName varchar(50) not null,
    constraint tblStatus_StatusName_uindex
        unique (StatusName)
);

create table tblCamera
(
    CameraId       varchar(36)  not null
        primary key DEFAULT (uuid()),
    CameraName     varchar(150) not null,
    ImageURL       text         not null,
    MACAddress     varchar(50)  not null,
    RTSPString     varchar(250) not null,
    TypeDetect     int          not null,
    CreatedTime    datetime     not null,
    UpdatedTime    datetime     null,
    ReasonInactive varchar(250) null,
    StatusId       int          not null,
    IPAddress      varchar(50)  not null,
    constraint tblCamera_CameraName_uindex
        unique (CameraName),
    constraint tblCamera_IPAddress_uindex
        unique (IPAddress),
    constraint tblCamera_MACAddress_uindex
        unique (MACAddress),
    constraint tblCamera_RTSPString_uindex
        unique (RTSPString),
    constraint FK_217
        foreign key (StatusId) references tblStatus (StatusId)
);

create index fkIdx_218
    on tblCamera (StatusId);

create table tblCategory
(
    CategoryId   int auto_increment
        primary key,
    CategoryName varchar(50) not null,
    StatusId     int         not null,
    constraint tblCategory_CategoryName_uindex
        unique (CategoryName),
    constraint FK_248
        foreign key (StatusId) references tblStatus (StatusId)
);

create index fkIdx_249
    on tblCategory (StatusId);

create table tblProduct
(
    ProductId      varchar(36)  not null
        primary key DEFAULT (uuid()),
    ProductName    varchar(150) not null,
    ImageURL       text         not null,
    Description    varchar(250) not null,
    CreatedTime    datetime     not null,
    UpdatedTime    datetime     null,
    ReasonInactive varchar(250) null,
    StatusId       int          not null,
    constraint tblProduct_ProductName_uindex
        unique (ProductName),
    constraint FK_229
        foreign key (StatusId) references tblStatus (StatusId)
);

create index fkIdx_230
    on tblProduct (StatusId);

create table tblProductCategoryMapping
(
    ProductCategoryMappingId varchar(36) not null
        primary key DEFAULT (uuid()),
    ProductId                varchar(36) not null,
    CategoryId               int         not null,
    constraint FK_89
        foreign key (ProductId) references tblProduct (ProductId),
    constraint FK_92
        foreign key (CategoryId) references tblCategory (CategoryId)
);

create index fkIdx_90
    on tblProductCategoryMapping (ProductId);

create index fkIdx_93
    on tblProductCategoryMapping (CategoryId);

create table tblStore
(
    StoreId        varchar(36)  not null
        primary key DEFAULT (uuid()),
    StoreName      varchar(150) not null,
    ImageURL       text         null,
    Address        varchar(250) not null,
    CreatedTime    datetime     not null,
    UpdatedTime    datetime     null,
    ReasonInactive varchar(250) null,
    DistrictId     int          not null,
    StatusId       int          not null,
    constraint tblStore_StoreName_uindex
        unique (StoreName),
    constraint FK_208
        foreign key (StatusId) references tblStatus (StatusId),
    constraint FK_51
        foreign key (DistrictId) references tblDistrict (DistrictId)
);

create table tblShelf
(
    ShelfId        varchar(36)  not null
        primary key DEFAULT (uuid()),
    ShelfName      varchar(150) not null,
    Description    varchar(250) not null,
    NumberStacks   int          not null,
    CreatedTime    datetime     not null,
    UpdatedTime    datetime     null,
    ReasonInactive varchar(250) null,
    StoreId        varchar(36)  not null,
    StatusId       int          not null,
    constraint tblShelf_ShelfName_uindex
        unique (ShelfName, StoreId),
    constraint FK_223
        foreign key (StatusId) references tblStatus (StatusId),
    constraint FK_35
        foreign key (StoreId) references tblStore (StoreId)
);

create index fkIdx_224
    on tblShelf (StatusId);

create index fkIdx_36
    on tblShelf (StoreId);

create table tblShelfCameraMapping
(
    ShelfCameraMappingId varchar(36) not null
        primary key DEFAULT (uuid()),
    AddedTime            datetime    null,
    RemovedTime          datetime    null,
    ShelfId              varchar(36) not null,
    CameraId             varchar(36) not null,
    StatusId             int         not null,
    constraint FK_220
        foreign key (StatusId) references tblStatus (StatusId),
    constraint FK_58
        foreign key (ShelfId) references tblShelf (ShelfId),
    constraint FK_61
        foreign key (CameraId) references tblCamera (CameraId)
);

create table tblHotSpot
(
    HotspotId            varchar(36)  not null
        primary key DEFAULT (uuid()),
    StartedTime          datetime     not null,
    EndedTime            datetime     not null,
    TotalPeople          bigint       not null,
    Description          varchar(250) null,
    ShelfCameraMappingId varchar(36)  not null,
    CreatedTime          datetime     null,
    UpdatedTime          datetime     null,
    constraint FK_262
        foreign key (ShelfCameraMappingId) references tblShelfCameraMapping (ShelfCameraMappingId)
);

create index fkIdx_263
    on tblHotSpot (ShelfCameraMappingId);

create index fkIdx_221
    on tblShelfCameraMapping (StatusId);

create index fkIdx_59
    on tblShelfCameraMapping (ShelfId);

create index fkIdx_62
    on tblShelfCameraMapping (CameraId);

create table tblStack
(
    StackId        varchar(36)  not null
        primary key DEFAULT (uuid()),
    Position       int          not null,
    CreatedTime    datetime     not null,
    UpdatedTime    datetime     null,
    ReasonInactive varchar(250) null,
    ShelfId        varchar(36)  not null,
    StatusId       int          not null,
    constraint FK_226
        foreign key (StatusId) references tblStatus (StatusId),
    constraint FK_38
        foreign key (ShelfId) references tblShelf (ShelfId)
);

create index fkIdx_227
    on tblStack (StatusId);

create index fkIdx_39
    on tblStack (ShelfId);

create table tblStackProductCameraMapping
(
    StackProductCameraMappingId varchar(36) not null
        primary key DEFAULT (uuid()),
    AddedCameraTime             datetime    null,
    RemovedCameraTime           datetime    null,
    AddedProductTime            datetime    null,
    RemoveProductTime           datetime    null,
    StackId                     varchar(36) not null,
    ProductId                   varchar(36) null,
    CameraId                    varchar(36) null,
    StatusId                    int         not null,
    constraint FK_197
        foreign key (ProductId) references tblProduct (ProductId),
    constraint FK_232
        foreign key (StatusId) references tblStatus (StatusId),
    constraint FK_69
        foreign key (StackId) references tblStack (StackId),
    constraint FK_74
        foreign key (CameraId) references tblCamera (CameraId)
);

create table tblEmotion
(
    EmotionId                   varchar(36)  not null
        primary key DEFAULT (uuid()),
    StartedTime                 datetime     not null,
    EndedTime                   datetime     not null,
    Angry                       bigint       not null,
    Disgust                     bigint       not null,
    Fear                        bigint       not null,
    Happy                       bigint       not null,
    Sad                         bigint       not null,
    Surprise                    bigint       not null,
    Neutral                     bigint       not null,
    Description                 varchar(250) null,
    StackProductCameraMappingId varchar(36)  not null,
    CreatedTime                 datetime     null,
    UpdatedTime                 datetime     null,
    constraint FK_245
        foreign key (StackProductCameraMappingId) references tblStackProductCameraMapping (StackProductCameraMappingId)
);

create index fkIdx_246
    on tblEmotion (StackProductCameraMappingId);

create index fkIdx_198
    on tblStackProductCameraMapping (ProductId);

create index fkIdx_233
    on tblStackProductCameraMapping (StatusId);

create index fkIdx_70
    on tblStackProductCameraMapping (StackId);

create index fkIdx_75
    on tblStackProductCameraMapping (CameraId);

create index fkIdx_209
    on tblStore (StatusId);

create index fkIdx_52
    on tblStore (DistrictId);

create table tblUser
(
    UserId         varchar(36)  not null
        primary key DEFAULT (uuid()),
    ImageURL       text         not null,
    Username       varchar(50)  not null,
    Password       text         not null,
    Fullname       varchar(150) not null,
    Gender         int          not null,
    BirthDate      date         not null,
    IdentifyCard   varchar(50)  not null,
    Phone          varchar(50)  not null,
    Email          varchar(250) not null,
    Address        varchar(250) not null,
    CreatedTime    datetime     not null,
    UpdatedTime    datetime     null,
    ReasonInactive varchar(250) null,
    RoleId         int          not null,
    DistrictId     int          not null,
    StatusId       int          not null,
    constraint tblUser_Email_uindex
        unique (Email),
    constraint tblUser_IdentifyCard_uindex
        unique (IdentifyCard),
    constraint tblUser_Username_uindex
        unique (Username),
    constraint FK_214
        foreign key (StatusId) references tblStatus (StatusId),
    constraint FK_31
        foreign key (RoleId) references tblRole (RoleId),
    constraint FK_44
        foreign key (DistrictId) references tblDistrict (DistrictId)
);

create index fkIdx_215
    on tblUser (StatusId);

create index fkIdx_32
    on tblUser (RoleId);

create index fkIdx_45
    on tblUser (DistrictId);

create table tblUserStoreMapping
(
    UserStoreMappingId varchar(36) not null
        primary key DEFAULT (uuid()),
    AddedTime          datetime    not null,
    RemovedTime        datetime    null,
    StoreId            varchar(36) not null,
    UserId             varchar(36) not null,
    StatusId           int         not null,
    constraint FK_128
        foreign key (StoreId) references tblStore (StoreId),
    constraint FK_131
        foreign key (UserId) references tblUser (UserId),
    constraint FK_211
        foreign key (StatusId) references tblStatus (StatusId)
);

create index fkIdx_129
    on tblUserStoreMapping (StoreId);

create index fkIdx_132
    on tblUserStoreMapping (UserId);

create index fkIdx_212
    on tblUserStoreMapping (StatusId);

create table tblVideo
(
    VideoId                     varchar(36)  not null
        primary key DEFAULT (uuid()),
    VideoName                   varchar(150) not null,
    VideoURL                    text         not null,
    StartedTime                 datetime     not null,
    EndedTime                   datetime     null,
    ShelfCameraMappingId        varchar(36)  null,
    StackProductCameraMappingId varchar(36)  null,
    UpdatedTime                 datetime     null,
    CreatedTime                 datetime     null,
    StatusId                    int          not null,
    constraint FK_186
        foreign key (ShelfCameraMappingId) references tblShelfCameraMapping (ShelfCameraMappingId),
    constraint FK_189
        foreign key (StackProductCameraMappingId) references tblStackProductCameraMapping (StackProductCameraMappingId),
    constraint FK_235
        foreign key (StatusId) references tblStatus (StatusId)
);

create index fkIdx_187
    on tblVideo (ShelfCameraMappingId);

create index fkIdx_190
    on tblVideo (StackProductCameraMappingId);

create index fkIdx_236
    on tblVideo (StatusId);




#Data City
INSERT INTO CFFE_Database.tblCity (CityId, CityName) VALUES (2, 'Ha Noi');
INSERT INTO CFFE_Database.tblCity (CityId, CityName) VALUES (1, 'Ho Chi Minh');

#Data District
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (1, 1, 'District 1');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (2, 1, 'District 2');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (3, 1, 'District 3');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (4, 1, 'District 4');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (5, 1, 'District 5');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (6, 1, 'District 6');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (7, 1, 'District 7');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (8, 1, 'District 8');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (9, 1, 'District 9');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (10, 1, 'District 10');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (11, 1, 'District 11');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (12, 1, 'District 12');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (13, 2, 'Ba Dinh District');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (14, 2, 'Bac Tu Liem District');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (15, 2, 'Cau giay District');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (16, 2, 'Dong Da District');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (17, 2, 'Ha Dong District');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (18, 2, 'Hai Ba Trung District');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (19, 2, 'Hoan Kiem District');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (20, 2, 'Hoan Mai District');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (21, 2, 'Long Bien District');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (22, 2, 'Nam Tu Liem District');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (23, 2, 'Tay Ho District');
INSERT INTO CFFE_Database.tblDistrict (DistrictId, CityId, DistrictName) VALUES (24, 2, 'Thanh Xuan District');

#Data Role
INSERT INTO CFFE_Database.tblRole (RoleId, RoleName) VALUES (1, 'Admin');
INSERT INTO CFFE_Database.tblRole (RoleId, RoleName) VALUES (2, 'Manager');

#Data Status
INSERT INTO CFFE_Database.tblStatus (StatusId, StatusName) VALUES (1, 'Active');
INSERT INTO CFFE_Database.tblStatus (StatusId, StatusName) VALUES (2, 'Inactive');
INSERT INTO CFFE_Database.tblStatus (StatusId, StatusName) VALUES (3, 'Pending');

#Account
INSERT INTO CFFE_Database.tblUser (UserId, ImageURL, Username, Password, Fullname, Gender, BirthDate, IdentifyCard, Phone, Email, Address, CreatedTime, UpdatedTime, ReasonInactive, RoleId, DistrictId, StatusId) VALUES ('1429ac27-f466-11eb-b193-42010a940020', 'https://storage.googleapis.com/capstone_storeage/images/default-image.jpg', 'minhnt', 'e861cbd39388a4ab9c33444d800920010158a8d97bbb4410254bc94d81603750', 'Nguyen Thanh Minh', 0, '2021-08-01', '837838383', '0938383876', 'namejs@gmail.com', '100 le van viet', '2021-08-03 21:21:22', '2021-08-03 23:05:59', null, 2, 8, 3);
INSERT INTO CFFE_Database.tblUser (UserId, ImageURL, Username, Password, Fullname, Gender, BirthDate, IdentifyCard, Phone, Email, Address, CreatedTime, UpdatedTime, ReasonInactive, RoleId, DistrictId, StatusId) VALUES ('1d5e9096-e8a0-11eb-859f-42010a940015', 'https://storage.googleapis.com/download/storage/v1/b/capstone_storeage/o/images%2F6afa07f7-53a1-4662-94ed-b9db9c4456c4-20210719-144823.jpg?generation=1626706103586451&alt=media', 'hieunt', '865028d8fa2815edfb1d47d63b02b39643908f66e6650089973d9e25e608e536', 'Nguyen Thanh Hieu', 1, '1999-07-20', '2424242424', '0392997510', 'hieu987020@gmail.com', '46 duong so 18, phuong Binh Hung Hoa', '2021-07-19 21:46:35', '2021-08-02 18:33:21', null, 2, 10, 1);
INSERT INTO CFFE_Database.tblUser (UserId, ImageURL, Username, Password, Fullname, Gender, BirthDate, IdentifyCard, Phone, Email, Address, CreatedTime, UpdatedTime, ReasonInactive, RoleId, DistrictId, StatusId) VALUES ('36f503e7-ede5-11eb-9da8-42010a94001d', 'https://storage.googleapis.com/download/storage/v1/b/capstone_storeage/o/images%2Fb37bdffb-89ae-458f-9f12-0bd91628f78f-20210802-082012.jpg?generation=1627892412921756&alt=media', 'hieutn', '865028d8fa2815edfb1d47d63b02b39643908f66e6650089973d9e25e608e536', 'Tran Ngoc Hieu', 1, '1996-07-11', '56789567891', '0392997517', 'hieuntse130434@fpt.edu.vn', '356 Son La', '2021-07-26 14:43:49', '2021-08-02 15:20:13', 'test', 2, 24, 1);
INSERT INTO CFFE_Database.tblUser (UserId, ImageURL, Username, Password, Fullname, Gender, BirthDate, IdentifyCard, Phone, Email, Address, CreatedTime, UpdatedTime, ReasonInactive, RoleId, DistrictId, StatusId) VALUES ('466de963-f40b-11eb-b193-42010a940020', 'https://storage.googleapis.com/download/storage/v1/b/capstone_storeage/o/images%2F3f6392b1-7f92-40b7-b85c-731ddf057b10-20210803-033122.jpg?generation=1627961482341968&alt=media', 'congnt', 'ef366b6c4777fcd1a3539eda09d2e2a90aa7c8bbf7bc5dac63b59183618dbaae', 'Nguyen Thanh Cong', 0, '1999-08-11', '0384848847', '0392997510', 'hieu9870201@gmail.com', '356 Le Van Viet', '2021-08-03 10:31:22', '2021-08-03 10:33:34', null, 2, 9, 1);
INSERT INTO CFFE_Database.tblUser (UserId, ImageURL, Username, Password, Fullname, Gender, BirthDate, IdentifyCard, Phone, Email, Address, CreatedTime, UpdatedTime, ReasonInactive, RoleId, DistrictId, StatusId) VALUES ('6812d40c-e89c-11eb-859f-42010a940015', 'https://storage.googleapis.com/download/storage/v1/b/capstone_storeage/o/images%2F4e6e20fe-ec36-46a4-89e0-b776648b4a6e-20210719-143527.jpg?generation=1626705327783141&alt=media', 'luannm', '865028d8fa2815edfb1d47d63b02b39643908f66e6650089973d9e25e608e536', 'Nguyen Minh Luan', 0, '1997-09-21', '285885959', '0336808288', 'vnproben@gmail.com', '9View Apartment', '2021-07-19 21:20:02', '2021-07-31 19:02:03', null, 2, 9, 1);
INSERT INTO CFFE_Database.tblUser (UserId, ImageURL, Username, Password, Fullname, Gender, BirthDate, IdentifyCard, Phone, Email, Address, CreatedTime, UpdatedTime, ReasonInactive, RoleId, DistrictId, StatusId) VALUES ('6b698282-e923-11eb-92dc-42010a940016', 'https://storage.googleapis.com/download/storage/v1/b/capstone_storeage/o/images%2F03626c3b-8bc2-4b40-b6ac-3f129bec5628-20210720-062629.jpeg?generation=1626762389574832&alt=media', 'truongtn', '63eb05eb3f91f5de154bd94517191ab76d74845ac793e0a7bd8a9954cc93f057', 'Than Nhat Truong', 0, '1998-02-17', '99921819283', '0971601583', 'truongtnse130426@fpt.edu.vn.com', '2083 Hung Vuong', '2021-07-20 13:26:30', '2021-08-01 23:33:31', null, 2, 1, 1);
INSERT INTO CFFE_Database.tblUser (UserId, ImageURL, Username, Password, Fullname, Gender, BirthDate, IdentifyCard, Phone, Email, Address, CreatedTime, UpdatedTime, ReasonInactive, RoleId, DistrictId, StatusId) VALUES ('e23cbd24-ead9-11eb-a0cb-42010a940018', 'https://storage.googleapis.com/download/storage/v1/b/capstone_storeage/o/images%2F74fa72af-128c-476c-9d65-8339e95c6b17-20210722-104507.jpeg?generation=1626950708158426&alt=media', 'huudn', '865028d8fa2815edfb1d47d63b02b39643908f66e6650089973d9e25e608e536', 'Do Ngoc Huu', 0, '1995-07-16', '215302577', '0397189696', 'ngochuu.bts@gmail.com', '8 street', '2021-07-22 17:45:08', '2021-08-03 23:31:54', null, 2, 9, 1);
INSERT INTO CFFE_Database.tblUser (UserId, ImageURL, Username, Password, Fullname, Gender, BirthDate, IdentifyCard, Phone, Email, Address, CreatedTime, UpdatedTime, ReasonInactive, RoleId, DistrictId, StatusId) VALUES ('ee1c53c1-f1dd-11eb-80a5-42010a940020', 'https://storage.googleapis.com/download/storage/v1/b/capstone_storeage/o/images%2F47fc2411-5c4a-41cd-8e3a-7903bc00658d-20210802-082151.jpg?generation=1627892511705386&alt=media', 'truongtn1', '63eb05eb3f91f5de154bd94517191ab76d74845ac793e0a7bd8a9954cc93f057', 'Than Nhat Truong', 0, '1999-07-11', '1111111112', '1111111112', 'thannhattruong99@gmail.com', '22 CMT8', '2021-07-31 16:01:44', '2021-08-04 12:51:46', null, 2, 1, 3);
INSERT INTO CFFE_Database.tblUser (UserId, ImageURL, Username, Password, Fullname, Gender, BirthDate, IdentifyCard, Phone, Email, Address, CreatedTime, UpdatedTime, ReasonInactive, RoleId, DistrictId, StatusId) VALUES ('f4a9495d-ce89-11eb-ac93-42010a94003f', 'https://storage.googleapis.com/download/storage/v1/b/capstone_storeage/o/images%2F061d250f-2ec3-44d4-862c-c7d68cbaddff-20210726-084337.jpg?generation=1627289017797554&alt=media', 'admin', '865028d8fa2815edfb1d47d63b02b39643908f66e6650089973d9e25e608e536', 'Administrator', 0, '2021-06-16', '999999999', '0999999999', 'admin@gmail.com', '99 Le Duan', '2021-06-16 17:02:27', '2021-07-26 15:43:38', null, 1, 1, 3);