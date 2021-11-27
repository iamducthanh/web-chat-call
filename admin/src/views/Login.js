import React, {useEffect} from 'react'
import userApi from '../api/userApi'
import dashboardApi from '../api/dashboardApi'
import { Link, useHistory } from 'react-router-dom'

const Login = () => {
  let history = useHistory();

    const login = async () => {
        let data = {
            username: document.getElementById('username').value,
            password: document.getElementById('userpassword').value,
        }
        let token = null;
        try {
          token = await userApi.getToken(data);
          if(token.data.access_token == 'USER'){
            showAlert();
          } else {
            console.log(token.data.access_token);
            sessionStorage.setItem("accessToken", "Bearer " + token.data.access_token);
            sessionStorage.setItem("username", data.username);
            history.push("/trang-chu");
          }
        } catch (error) {
          showAlert();
        }
    }

    function closeAlert() {
      document.getElementById('errorAlert').style.display = 'none';
    }

    function showAlert() {
      document.getElementById('errorAlert').style.display = 'unset';
      document.getElementById('errormessage').innerHTML = 'Thông tin tài khoản hoặc mật khẩu không chính xác!';
    }

    return (
      <div>
      <Link to="/trang-chu" style={{display: 'none'}} id="trangChu"></Link>
  <div style={{display: 'none'}} id="errorAlert" className="swal2-container swal2-center swal2-backdrop-show">
  <div aria-labelledby="swal2-title" aria-describedby="swal2-content" className="swal2-popup swal2-modal swal2-icon-error swal2-show" tabIndex={-1} role="dialog" aria-live="assertive" aria-modal="true" style={{display: 'flex'}}>
    <div className="swal2-header">
      <div className="swal2-icon swal2-error swal2-icon-show" style={{display: 'flex'}}><span className="swal2-x-mark">
          <span className="swal2-x-mark-line-left" />
          <span className="swal2-x-mark-line-right" />
        </span>
      </div>
      <h2 className="swal2-title" id="errormessage" style={{display: 'flex'}}>Thông tin tài khoản hoặc mật khẩu không chính xác!</h2>
    </div>
    <div className="swal2-actions">
      <div className="swal2-loader" />
      <button type="button" onClick={closeAlert} className="swal2-confirm swal2-styled" id="closeAlertError" aria-label style={{display: 'inline-block'}}>OK
      </button>
    </div>
  </div>
</div>

          <div className="swal2-container swal2-center swal2-backdrop-show" style={{ overflowY: "auto", display: "none" }}
        id="loginFalse"
      >
        <div aria-labelledby="swal2-title" id="profile" aria-describedby="swal2-content" className="swal2-popup swal2-modal swal2-show"
          tabIndex={-1} role="dialog" aria-live="assertive" aria-modal="true" style={{ display: "flex", width: "50%", marginTop: "10%" }}
        >
          <button type="button" className="swal2-close">
            ×
          </button>
       
          <div className="tabTTKH">
            <div style={{marginTop: '20px'}}>
              <h5>Thông tin tài khoản mật khẩu không chính xác!</h5>
            </div>
            <div className="footerAddHH" style={{ textAlign: "center" }}>
              <button className="btn btn-primary"
                style={{ backgroundColor: "#656565", border: "none" }}
              ><i class="fas fa-ban"></i> Đóng
              </button>
            </div>
          </div>
        </div>
      </div>
  {/* <div id="preloader"><div id="status"><div className="spinner" /></div></div> */}
  <div className="accountbg" />
  <div className="account-pages mt-5 pt-5">
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-5 col-xl-4">
          <div className="card">
            <div className="card-body">
              <div className="d-flex p-3">
                <div>
                  <a href="index.html" tppabs="https://themesbrand.com/foxia/layouts/blue/index.html" className>
                    <img src="/admin/logo_dark.png" tppabs="https://themesbrand.com/foxia/layouts/blue/assets/images/logo_dark.png" alt height={22} className="auth-logo logo-dark" />
                    <img src="/admin/logo.png" tppabs="https://themesbrand.com/foxia/layouts/blue/assets/images/logo.png" alt height={22} className="auth-logo logo-light" />
                  </a>
                </div>
                <div className="ms-auto text-end">
                  <h4 className="font-size-18">Welcome Back !</h4>
                  <p className="text-muted mb-0">Sign in to continue to Foxia.</p>
                </div>
              </div>
              <div className="p-3">
                <div className="form-horizontal" action="https://themesbrand.com/foxia/layouts/blue/index.html">
                  <div className="mb-3">
                    <label className="form-label" htmlFor="username">Tên đăng nhập</label>
                    <input type="text" id="username" className="form-control" id="username" placeholder="Tên đăng nhập" />
                  </div>
                  <div className="mb-3">
                    <label className="form-label" htmlFor="userpassword">Mật khẩu</label>
                    <input type="password" className="form-control" id="userpassword" placeholder="Mật khẩu" />
                  </div>
                  <div className="row mt-4">
                    <div className="col-sm-6">
                      <div className="form-check">
                        <input className="form-check-input" type="checkbox" defaultValue id="customControlInline" />
                        <label className="form-check-label" htmlFor="customControlInline">
                          Remember me
                        </label>
                      </div>
                    </div>
                    <div className="col-sm-6 text-end">
                      <button className="btn btn-primary w-md waves-effect waves-light" onClick={login} type="submit">Đăng nhập</button>
                    </div>
                  </div>
                  <div className="mb-0 row">
                    <div className="col-12 mt-4 text-center">
                      <a href="pages-recoverpw.html" tppabs="https://themesbrand.com/foxia/layouts/blue/pages-recoverpw.html" className="text-muted"><i className="mdi mdi-lock" /> Forgot your password?</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="mt-5 text-center position-relative">
            <p className="text-white-50"> ©   Foxia. Crafted with <i className="mdi mdi-heart text-danger" /> by Themesbrand</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

    )
}

export default Login
