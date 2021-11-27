import React from 'react'
import { Link, useHistory } from 'react-router-dom'

const Header = () => {
  let history = useHistory();
    const logout = () => {
      sessionStorage.removeItem('accessToken');
      history.push("/dang-nhap");
    }

    return (
<header id="page-topbar" style={{backgroundColor: 'red'}}>
  <div className="topnav" style={{backgroundColor: '#655be6'}}>
    <div className="navbar-header mx-auto">
      <div className="d-flex">
        {/* LOGO */}
        <div className="navbar-brand-box d-none d-lg-block">
          <a href="index.html" tppabs="https://themesbrand.com/foxia/layouts/blue/index.html" className="logo logo-light">
            <span className="logo-sm">
              <img src="/admin/logo-sm.png" tppabs="https://themesbrand.com/foxia/layouts/blue/assets/images/logo-sm.png" alt height={22} />
            </span>
            <span className="logo-lg">
              <img src="/admin/logo.png" tppabs="https://themesbrand.com/foxia/layouts/blue/assets/images/logo.png" alt height={20} />
            </span>
          </a>
        </div>
        <nav className="navbar navbar-light navbar-expand-lg topnav-menu">
          <div className="collapse navbar-collapse" id="topnav-menu-content">
            <ul className="navbar-nav">
              <li className="nav-item">
                <Link style={{color: 'white'}} className="nav-link" to="/trang-chu">
                  <i className="dripicons-meter me-2" />Thống kê
                </Link>
              </li>
              <li className="nav-item"> 
                <Link style={{color: 'white'}} className="nav-link" to="/nguoi-dung">
                  <i className="far fa-user"/> Người dùng
                </Link>
              </li>
            </ul>
          </div>
        </nav>
      </div>
      <div className="d-flex">
        <div style={{color: 'white'}} className="dropdown d-inline-block ms-2">
          <button type="button" className="btn header-item noti-icon waves-effect" id="page-header-notifications-dropdown" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i className="mdi mdi-bell-outline" style={{color: 'white'}} />
            <span className="badge bg-danger rounded-pill">3</span>
          </button>
          <div className="dropdown-menu dropdown-menu-lg dropdown-menu-end p-0" aria-labelledby="page-header-notifications-dropdown">
            <div className="p-3">
              <div className="row align-items-center">
                <div className="col">
                  <h5 className="m-0 font-size-16"> Notification (3) </h5>
                </div>
              </div>
            </div>
            <div data-simplebar style={{maxHeight: 230}}>
              <a href className="text-reset notification-item">
                <div className="d-flex">
                  <div className="avatar-xs me-3">
                    <span className="avatar-title bg-success rounded-circle font-size-16">
                      <i className="mdi mdi-cart-outline" />
                    </span>
                  </div>
                  <div className="flex-1">
                    <h6 className="mt-0 font-size-15 mb-1">Your order is placed</h6>
                    <div className="font-size-12 text-muted">
                      <p className="mb-1">Dummy text of the printing and typesetting industry.
                      </p>
                    </div>
                  </div>
                </div>
              </a>
            </div>
            <div className="p-2 border-top">
              <a className="btn btn-sm btn-link font-size-14 btn-block" href="#">
                View all
              </a>
            </div>
          </div>
        </div>
        <div className="dropdown d-inline-block ms-2">
          <button type="button" className="btn header-item waves-effect" id="page-header-user-dropdown" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <img className="rounded-circle header-profile-user" src="/admin/avatar-6.jpg" tppabs="https://themesbrand.com/foxia/layouts/blue/assets/images/users/avatar-6.jpg" alt="Header Avatar" />
            <span style={{color: 'white'}} className="d-none d-md-inline-block ms-1">Donald T. <i className="mdi mdi-chevron-down" /> </span>
          </button>
          <div className="dropdown-menu dropdown-menu-end">
            <div className="dropdown-item" onClick={logout}><i className="dripicons-exit font-size-16 align-middle me-2 text-muted" /> Đăng xuất</div>
          </div>
        </div>
        <div className="dropdown d-inline-block">
          <button type="button" className="btn header-item noti-icon right-bar-toggle waves-effect">
            <i className="mdi mdi-spin mdi-cog" />
          </button>
        </div>
      </div>
    </div>
  </div>
</header>
    )
}

export default Header
