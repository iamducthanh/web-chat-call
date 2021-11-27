import React from "react";
import Footer from "../component/Footer";
import Header from "../component/Header";

const AdminLayout = (props) => {
  return (
    <div>
      {/* <div id="preloader">
        <div id="status">
          <div className="spinner" />
        </div>
      </div> */}
      <div id="layout-wrapper">
        <Header />
        <div className="main-content" style={{ marginLeft: "0px" }}>
        {props.children}
        <Footer/>
        </div>
      </div>
    </div>
  );
};

export default AdminLayout;
