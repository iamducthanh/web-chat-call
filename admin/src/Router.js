import React from "react";
import { BrowserRouter, Switch, Redirect, Route, useHistory } from "react-router-dom";
import AdminLayout from "./layout/AdminLayout";
import Dashboard from "./views/Dashboard";
import DataTable from "./views/DataTable";
import Login from "./views/Login";
import User from "./views/User";

const Router = (props) => {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path="/dang-nhap">
          <Login/>
        </Route>
        <Route exact path="/table">
          <DataTable/>
        </Route>
        <Route exact path="/*"
          render={()=>(
            sessionStorage.getItem("accessToken") ?
            <AdminLayout {...props}>
            <Switch>
              <Route exact path="/trang-chu" 
                ><Dashboard />
              </Route>
     
              <Route exact path="/nguoi-dung" 
                ><User />
              </Route>
            </Switch>
          </AdminLayout>
          :
          <Redirect to="/dang-nhap" />
          )}
        ></Route>
      </Switch>
    </BrowserRouter>
  );
};

export default Router;
