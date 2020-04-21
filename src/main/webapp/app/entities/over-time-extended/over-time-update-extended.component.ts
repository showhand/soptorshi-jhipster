import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { OverTimeExtendedService } from './over-time-extended.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { OverTimeUpdateComponent } from 'app/entities/over-time';
import { IManager } from 'app/shared/model/manager.model';
import { Account, AccountService } from 'app/core';
import { ManagerService } from 'app/entities/manager';

@Component({
    selector: 'jhi-over-time-update-extended',
    templateUrl: './over-time-update-extended.component.html'
})
export class OverTimeUpdateExtendedComponent extends OverTimeUpdateComponent {
    currentAccount: Account;
    currentEmployee: IEmployee;
    employeesUnderSupervisor: IManager[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected overTimeService: OverTimeExtendedService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected managerService: ManagerService
    ) {
        super(jhiAlertService, overTimeService, employeeService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ overTime }) => {
            this.overTime = overTime;
            this.fromTime = this.overTime.fromTime != null ? this.overTime.fromTime.format(DATE_TIME_FORMAT) : null;
            this.toTime = this.overTime.toTime != null ? this.overTime.toTime.format(DATE_TIME_FORMAT) : null;
            this.createdOn = this.overTime.createdOn != null ? this.overTime.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.overTime.updatedOn != null ? this.overTime.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        /*this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));*/

        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.employeeService
                .query({
                    'employeeId.equals': this.currentAccount.login
                })
                .subscribe(
                    (res: HttpResponse<IEmployee[]>) => {
                        this.currentEmployee = res.body[0];
                        this.managerService
                            .query({
                                'employeeId.equals': this.currentEmployee.id
                            })
                            .subscribe(
                                (res: HttpResponse<IManager[]>) => {
                                    this.employeesUnderSupervisor = res.body;
                                    const map: string = this.employeesUnderSupervisor.map(val => val.parentEmployeeId).join(',');
                                    this.employeeService
                                        .query({
                                            'id.in': [map]
                                        })
                                        .subscribe(
                                            (res: HttpResponse<IEmployee[]>) => (this.employees = res.body),
                                            (res: HttpErrorResponse) => this.onError(res.message)
                                        );
                                },
                                (res: HttpErrorResponse) => this.onError(res.message)
                            );
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });
    }
}
