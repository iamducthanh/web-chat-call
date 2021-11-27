import React from "react";
import { useEffect, useState } from "react";
import dashboardApi from "../api/dashboardApi";

const Dashboard = () => {
  const [tinNhan, setTinNhan] = useState([]);
  const [tkUser, setTkUser] = useState([]);
  useEffect(async () => {
    console.log(localStorage.getItem('accessToken'));
    let dataTinNhan = await dashboardApi.getData('2021');
    setTinNhan(dataTinNhan.data)
    let tkUser = await dashboardApi.getTKUser();
    console.log(tkUser);
    setTkUser(tkUser.data)
    window.jQuery.Dashboard.init();

  }, []);

  return (
    <div className="page-content">
      <audio style={{display:'none'}} id="tingting" src="https://github.com/iamducthanh/library/blob/main/tingting.mp3?raw=true" controls></audio>
      {tinNhan.map((slg) => (
              <input type="hidden" id={slg.month} value={slg.count} />

      ))}
      <input type="hidden" id="useronline" value={tkUser.online} />
      <input type="hidden" id="useroffline" value={tkUser.offline} />
      <div className="container-fluid">
        <div className="row">
          <div className="col-xl-9">
            <div className="card">
              <div className="card-body">
                <h1 className="mt-0 header-title mb-5">
                  Số lượng tin nhắn trong năm 2021
                </h1>

                <div
                  id="morris-bar-stacked"
                  className="morris-charts morris-chart-height"
                  dir="ltr"
                />
              </div>
            </div>
          </div>
          <div className="col-xl-3">
            <div className="col-xl-12">
              <div className="card mini-stats">
                <div className="p-3 mini-stats-content">
                  <div className="mb-4">
                    <div className="float-end text-end">
                      <span className="badge bg-light text-info my-2">
                        {" "}
                        + 11%{" "}
                      </span>
                      <p className="text-white-50">From previous period</p>
                    </div>
                    <span
                      style={{ color: "white" }}
                      className="peity-pie"
                      data-peity='{ "fill": ["rgba(255, 255, 255, 0.8)", "rgba(255, 255, 255, 0.2)"]}'
                      data-width={54}
                      data-height={54}
                    >
                      Người dùng
                    </span>
                  </div>
                </div>
                <div className="mx-3">
                  <div className="card mb-0 border p-3 mini-stats-desc">
                    <div className="d-flex">
                      <h6 className="mt-0 mb-3">Người dùng</h6>
                      <h5 className="ms-auto mt-0">{tkUser.quantity}</h5>
                    </div>
                    <p className="text-muted mb-0">
                      Số lượng người dùng trong hệ thống
                    </p>
                  </div>
                </div>
              </div>
            </div>
            <div className="col-xl-12">
              <div className="card">
                <div className="card-body">
                  <h4 className="mt-0 header-title">Trạng thái người dùng</h4>
                  <div
                    id="morris-donut-example"
                    className="dash-chart morris-charts text-center"
                  ></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
