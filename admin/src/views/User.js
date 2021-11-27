import React from "react";
import { useEffect, useState } from "react";
import { MDBDataTableV5 } from "mdbreact";
import userApi from "../api/userApi";
import { ToastContainer } from "react-toastr";
import "./toastr.css";
import "./animate.css";



const User = () => {
  let container1;

  const [datatable, setDatatable] = useState({});
  const [userDetail, setUserDetail] = useState({});

  let userDtoList = [];
  useEffect(async () => {
    loadUser()
  }, []);
  async function loadUser(){
    let userList = await userApi.getUsers(sessionStorage.getItem("username"));
    userList.data.map((user) => {
      console.log(user);
      console.log(user.status);
      userDtoList.push({
        id: user.id,
        fullname: user.fullName,
        email: user.email,
        phone: user.phone,
        birthDay: user.birthdayString,
        status: user.status ? <span class="badge bg-success">Active</span> : <span class="badge bg-danger">In active</span>,
        action: (
          <div>
            <button className="btn btn-primary" onClick={showUser.bind(this, user.id)} style={{ margin: "0 5px 0 0" }}>
              <i class="fas fa-edit"></i>
            </button>
            {/* <button className="btn btn-danger">
              <i class="bi bi-trash"></i>
            </button> */}
          </div>
        ),
      });
    });

    let data = {
      columns: [
        {
          label: "ID",
          field: "id",
          width: 150,
          attributes: {
            "aria-controls": "DataTable",
            "aria-label": "Name",
          },
        },
        {
          label: "Họ tên",
          field: "fullname",
          width: 270,
        },
        {
          label: "Email",
          field: "email",
          width: 200,
        },
        {
          label: "Số điện thoại",
          field: "phone",
          sort: "disabled",
          width: 100,
        },
        {
          label: "Ngày sinh",
          field: "birthDay",
          sort: "disabled",
          width: 150,
        },
        {
          label: "Trạng thái",
          field: "status",
          sort: "disabled",
          width: 100,
        },
        {
          label: "",
          field: "action",
          sort: "disabled",
          width: 100,
        },
      ],
      rows: userDtoList,
    };
    setDatatable(data)
  }
  let user = {
    birthDate: "",
    birthdayString: "",
    descreption: "",
    email: "",
    friendStatus: "",
    fullName: "",
    gender: true,
    id: 3,
    image: "",
    lastonline: "",
    online: false,
    phone: "",
    role: "",
    status: true,
    username: ""
  }
  const showUser = async (id) => {
    console.log(id);
    user = await userApi.getUser(id);
    setUserDetail(user.data)
    document.getElementById("modalProfile").style.display = "unset";
  };
  const closeUser = () => {
    document.getElementById("modalProfile").style.display = "none";
    loadUser();
  };

  function editMenuKH() {
    let left = document.getElementById("leftKH");
    let right = document.getElementById("rightKH");
    if (left.style.display == "none") {
      left.style.display = "unset";
      right.className = "col-lg-9";
    } else {
      left.style.display = "none";
      right.className = "col-lg-12";
    }
  }

  async function updateUser(){
    let id = document.getElementById("userId").value;
    let role = "";
    let rdoRole = document.getElementsByName('userRole');
    role = rdoRole[0].checked ? "ROLE_ADMIN" : "ROLE_USER";
    let status = 1;
    let rdoStatus = document.getElementsByName('userStatus');
    status = rdoStatus[0].checked ? 1 : 0;
    let data = {
      id: id,
      role: role,
      status: status
    }
    console.log(data);
    await userApi.updateUser(data);
    container1.success(
      <strong>Thành công</strong>,
      <em>Cập nhật thông tin thành công!</em>,
      {
        closeButton: true,
        timeOut: 1000
      }
    )
  }

  return (
    <div className="page-content container1">
      
        <ToastContainer
    ref={ref => container1 = ref}
    className="toast-top-right"
  />
      <div
        className="swal2-container swal2-center swal2-backdrop-show"
        style={{ overflowY: "auto", display: "none" }}
        id="modalProfile"
      >
        <div
          aria-labelledby="swal2-title"
          id="profile"
          aria-describedby="swal2-content"
          className="swal2-popup swal2-modal swal2-show"
          tabIndex={-1}
          role="dialog"
          aria-live="assertive"
          aria-modal="true"
          style={{ display: "flex", width: "80%" }}
        >
          <button type="button" className="swal2-close" onClick={closeUser}>
            ×
          </button>
          <div
            className="row"
            style={{ textAlign: "center", marginBottom: 20 }}
          >
            <div
              className="col-lg-12 menuTabKH"
              onclick="onTabTTKH(0);"
              id="menuTab1"
            >
              Thông tin người dùng
            </div>
          </div>
          <div className="tabTTKH">
            <div className="row">
              <div className="col-lg-3">
                <img
                  style={{ width: "100%", marginBottom: 30 }}
                  src={userDetail.image}
                  alt
                />
              </div>
              <div className="col-lg-5">
                <div className="row">
                  <input id="userId" value={userDetail.id} type="hidden"/>
                  <label
                    htmlFor="example-text-input"
                    className="col-sm-4 col-form-label font-size-14"
                  >
                    Tên người dùng:
                  </label>
                  <div className="col-sm-8">
                    <input
                      className="form-control myInputNoBorder"
                      type="text"
                      placeholder="Tên người dùng" value={userDetail.username}
                    />
                  </div>
                </div>
                <div className="row">
                  <label
                    htmlFor="example-text-input"
                    className="col-sm-4 col-form-label font-size-14"
                  >
                    Họ tên:
                  </label>
                  <div className="col-sm-8">
                    <input
                      className="form-control myInputNoBorder"
                      type="text"
                      placeholder="Họ" value={userDetail.fullName}
                    />
                  </div>
                </div>
                <div className="row">
                  <label
                    htmlFor="example-text-input"
                    className="col-sm-4 col-form-label font-size-14"
                  >
                    Ngày sinh:
                  </label>
                  <div className="col-sm-8">
                    <input
                      className="form-control myInputNoBorder"
                      type="date"
                      placeholder="Ngày sinh" value={userDetail.birthdayString}
                    />
                  </div>
                </div>

                <div className="row">
                  <label
                    htmlFor="example-text-input"
                    className="col-sm-4 col-form-label font-size-14"
                  >
                    Email:
                  </label>
                  <div className="col-sm-8">
                    <input
                      className="form-control myInputNoBorder"
                      type="text"
                      placeholder="Email" value={userDetail.email}
                    />
                  </div>
                </div>
                <div className="row">
                  <label
                    htmlFor="example-text-input"
                    className="col-sm-4 col-form-label font-size-14"
                  >
                    Hoạt động
                  </label>
                  <div className="col-sm-8">
                    <input
                      className="form-control myInputNoBorder"
                      type="text"
                      placeholder="Người tạo" value={userDetail.friendStatus}
                    />
                  </div>
                </div>
              </div>
              <div className="col-lg-4">
                <div className="row">
                  <label
                    htmlFor="example-text-input"
                    className="col-sm-4 col-form-label font-size-14"
                  >
                    Số điện thoại:
                  </label>
                  <div className="col-sm-8">
                    <input
                      className="form-control myInputNoBorder"
                      type="text"
                      placeholder="Số điện thoại" value={userDetail.phone}
                    />
                  </div>
                </div>
                <div className="row">
                  <label
                    htmlFor="example-text-input"
                    className="col-sm-4 col-form-label font-size-14"
                  >
                    Giới tính:
                  </label>
                  <div className="col-sm-8">
                    {userDetail.gender?"Nam":"Nữ"}
                  </div>
                </div>
                <div className="row">
                  <label
                    htmlFor="example-text-input"
                    className="col-sm-4 col-form-label font-size-14"
                  >
                    Vai trò:
                  </label>
                  <div className="col-sm-8">
                    {
                      userDetail.role == "ROLE_ADMIN" ? <input type="radio" value="ROLE_ADMIN" name="userRole" checked></input> : <input value="ROLE_ADMIN" type="radio" name="userRole"></input>
                    }
                    {" "}Admin{" "}
                    {
                      userDetail.role == "ROLE_USER" ? <input type="radio" value="ROLE_USER" name="userRole" checked></input> : <input value="ROLE_USER" type="radio" name="userRole"></input>
                    }{" "}
                    Người dùng
                  </div>
                </div>
                <div className="row">
                  <label
                    htmlFor="example-text-input"
                    className="col-sm-4 col-form-label font-size-14"
                  >
                    Mô tả ngắn:
                  </label>
                  <div className="col-sm-8">
                    <textarea
                      className="form-control myInputNoBorder"
                      name
                      id
                      cols={30}
                      rows={3}
                      defaultValue={userDetail.descreption}
                    />
                  </div>
                </div>
                <div className="row">
                  <label
                    htmlFor="example-text-input"
                    className="col-sm-4 col-form-label font-size-14"
                  >
                    Trạng thái:
                  </label>
                  <div className="col-sm-8">
                    {
                      userDetail.status ? <input type="radio" value="1" name="userStatus" checked></input> : <input value="1" type="radio" name="userStatus"></input>
                    }
                    {" "}Active{" "}
                    {
                      userDetail.status == false ? <input type="radio" value="0" name="userStatus" checked></input> : <input type="radio" value="0" name="userStatus"></input>
                    }{" "}
                    In active
                  </div>
                </div>
              </div>
            </div>
            <div className="footerAddHH" style={{ textAlign: "right" }}>
              <button
                className="btn btn-primary" onClick = {updateUser}
                style={{
                  border: "none",
                  marginRight: "5px",
                }}
              >
                <i className="fas fa-user-lock" />
                 Cập nhật
              </button>
              <button
                onClick={closeUser}
                className="btn btn-primary"
                style={{ backgroundColor: "#656565", border: "none" }}
              >
                <i class="fas fa-ban"></i> Quay lại
              </button>
            </div>
          </div>
        </div>
      </div>

      <div className="myContainer">
        <div className="row container1">
          <div className="col-lg-3 h-100" id="leftKH">
            <div className="card" id="formLeft">
              <div className="h-100 w-100">
                <div id="sidebar-menu">
                  <ul className="list-unstyled" id="side-menu">
                    <li>
                      <a className="has-arrow waves-effect">
                        <span> Nhóm khách hàng </span>
                      </a>
                      <ul
                        className="sub-menu"
                        style={{ padding: "0 10px 0 20px" }}
                      >
                        <div className="row">
                          <div className="col-lg-10">
                            <select className="form-select myInputNoBorder">
                              <option selected>Chọn nhóm khách hàng</option>
                              <option value={1}>Thêm nhóm</option>
                              <option value={2}>Nhóm 1</option>
                              <option value={3}>Nhóm 2</option>
                            </select>
                          </div>
                          <div className="col-lg-2">
                            <i className="fas fa-edit" />
                          </div>
                        </div>
                      </ul>
                    </li>
                    <li>
                      <a className="has-arrow waves-effect">
                        <span>Ngày tạo </span>
                      </a>
                      <ul
                        className="sub-menu"
                        style={{ padding: "0 10px 0 30px" }}
                      >
                        <div className="row">
                          <label
                            htmlFor="example-text-input"
                            className="col-sm-4 col-form-label font-size-14"
                          >
                            Bắt đầu:
                          </label>
                          <div className="col-sm-8">
                            <input
                              className="form-control myInputNoBorder"
                              type="date"
                              placeholder="Ngày sinh"
                            />
                          </div>
                        </div>
                        <div className="row">
                          <label
                            htmlFor="example-text-input"
                            className="col-sm-4 col-form-label font-size-14"
                          >
                            Kết thúc:
                          </label>
                          <div className="col-sm-8">
                            <input
                              className="form-control myInputNoBorder"
                              type="date"
                              placeholder="Ngày sinh"
                            />
                          </div>
                        </div>
                      </ul>
                    </li>
                    <li>
                      <a className="has-arrow waves-effect">
                        <span>Người tạo </span>
                      </a>
                      <ul
                        className="sub-menu"
                        style={{ padding: "0 10px 0 30px" }}
                      >
                        <select className="form-select myInputNoBorder">
                          <option selected>Chọn người tạo</option>
                          <option value={2}>Người 1</option>
                          <option value={3}>Người 2</option>
                        </select>
                      </ul>
                    </li>
                    <li>
                      <a className="has-arrow waves-effect">
                        <span>Tổng bán </span>
                      </a>
                      <ul
                        className="sub-menu"
                        style={{ padding: "0 10px 0 30px" }}
                      >
                        <div className="row">
                          <label
                            htmlFor="example-text-input"
                            className="col-sm-4 col-form-label font-size-14"
                          >
                            Từ:
                          </label>
                          <div className="col-sm-8">
                            <input
                              className="form-control myInputNoBorder"
                              type="number"
                              defaultValue={0}
                            />
                          </div>
                        </div>
                        <div className="row">
                          <label
                            htmlFor="example-text-input"
                            className="col-sm-4 col-form-label font-size-14"
                          >
                            Tới:
                          </label>
                          <div className="col-sm-8">
                            <input
                              className="form-control myInputNoBorder"
                              type="number"
                            />
                          </div>
                        </div>
                        <div className="row">
                          <label
                            htmlFor="example-text-input"
                            className="col-sm-4 col-form-label font-size-14"
                          >
                            Bắt đầu:
                          </label>
                          <div className="col-sm-8">
                            <input
                              className="form-control myInputNoBorder"
                              type="date"
                              placeholder="Ngày sinh"
                            />
                          </div>
                        </div>
                        <div className="row">
                          <label
                            htmlFor="example-text-input"
                            className="col-sm-4 col-form-label font-size-14"
                          >
                            Kết thúc:
                          </label>
                          <div className="col-sm-8">
                            <input
                              className="form-control myInputNoBorder"
                              type="date"
                              placeholder="Ngày sinh"
                            />
                          </div>
                        </div>
                      </ul>
                    </li>
                    <li>
                      <a className="has-arrow waves-effect">
                        <span>Nợ hiện tại </span>
                      </a>
                      <ul
                        className="sub-menu"
                        style={{ padding: "0 10px 0 30px" }}
                      >
                        <div className="row">
                          <label
                            htmlFor="example-text-input"
                            className="col-sm-4 col-form-label font-size-14"
                          >
                            Từ:
                          </label>
                          <div className="col-sm-8">
                            <input
                              className="form-control myInputNoBorder"
                              type="number"
                              defaultValue={0}
                            />
                          </div>
                        </div>
                        <div className="row">
                          <label
                            htmlFor="example-text-input"
                            className="col-sm-4 col-form-label font-size-14"
                          >
                            Tới:
                          </label>
                          <div className="col-sm-8">
                            <input
                              className="form-control myInputNoBorder"
                              type="number"
                            />
                          </div>
                        </div>
                      </ul>
                    </li>
                    <li>
                      <a className="has-arrow waves-effect">
                        <span>Loại khách hàng </span>
                      </a>
                      <ul
                        className="sub-menu"
                        style={{ padding: "0 10px 0 30px" }}
                      >
                        <input
                          className="form-check-input"
                          style={{ marginBottom: 10 }}
                          type="radio"
                          defaultChecked
                          name="typeKH"
                        />
                          Tất cả <br />
                        <input
                          className="form-check-input"
                          style={{ marginBottom: 10 }}
                          type="radio"
                          name="typeKH"
                        />
                          Công ty
                        <br />
                        <input
                          className="form-check-input"
                          style={{ marginBottom: 10 }}
                          type="radio"
                          name="typeKH"
                        />
                          Cá nhân
                        <br />
                      </ul>
                    </li>
                    <li>
                      <a className="has-arrow waves-effect">
                        <span>Giới tính </span>
                      </a>
                      <ul
                        className="sub-menu"
                        style={{ padding: "0 10px 0 30px" }}
                      >
                        <input
                          className="form-check-input"
                          style={{ marginBottom: 10 }}
                          type="radio"
                          defaultChecked
                          name="typeKH"
                        />
                          Tất cả <br />
                        <input
                          className="form-check-input"
                          style={{ marginBottom: 10 }}
                          type="radio"
                          name="typeKH"
                        />
                          Nam
                        <br />
                        <input
                          className="form-check-input"
                          style={{ marginBottom: 10 }}
                          type="radio"
                          name="typeKH"
                        />
                          Nữ
                        <br />
                      </ul>
                    </li>
                  </ul>
                </div>
                {/* Sidebar */}
              </div>
              <div className="card-body">
                <div
                  id="morris-donut-example"
                  className="dash-chart morris-charts text-center"
                />
              </div>
            </div>
          </div>
          <div className="col-lg-9" id="rightKH">
            <div className="card">
              <div className="card-body">
                <div className="row">
                  <div className="col-lg-4">
                    <h1 className="header-title mb-4">
                      <i
                        className="mdi mdi-menu"
                        style={{ fontSize: "16pt" }}
                        onClick={editMenuKH}
                      />
                       Danh sách người dùng
                    </h1>
                  </div>
                  <div className="col-lg-8">
                    <div className="dropdown myDropdown myBtn">
                      <button
                        className="btn btn-primary"
                        id="dropdownMenuButton"
                        data-bs-toggle="dropdown"
                        aria-expanded="false"
                      >
                        <i className="fas fa-filter" />
                        <i className="mdi mdi-chevron-down" />
                      </button>
                      <div
                        className="dropdown-menu dropdown-menu-end"
                        style={{ width: "650%" }}
                      >
                        <div className="row">
                          <div className="col-lg-6">
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Mã khách hàng
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Tên khách hàng
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Loại khách
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Điện thoại
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Nhóm khách hàng
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Giới tính
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Ngày sinh
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Email
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />
                              Facebook
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Công ty
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Mã số thuế
                            </div>
                          </div>
                          <div className="col-lg-6">
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Địa chỉ
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Khu vực giao hàng
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />
                              Phường/Xã
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Người tạo
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Ngày tạo
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Ghi chú
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Ngày giao dịch chuỗi
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Nợ hiện tại
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Tổng bán
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Tổng bán trừ hàng
                            </div>
                            <div className="loc">
                              <input
                                className="form-check-input"
                                type="checkbox"
                              />{" "}
                              Trạng thái
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="row">
                  <div className="col-lg-12">
                    <div className="card">
                      <div className="card-body">
                        <MDBDataTableV5
                          hover
                          entriesOptions={[5, 20, 25]}
                          entries={5}
                          pagesAmount={4}
                          data={datatable}
                          materialSearch
                        />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default User;
