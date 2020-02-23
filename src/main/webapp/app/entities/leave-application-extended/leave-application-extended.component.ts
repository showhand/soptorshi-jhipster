import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { LeaveApplicationExtendedService } from './leave-application-extended.service';
import { LeaveApplicationComponent } from 'app/entities/leave-application';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { EmployeeService } from 'app/entities/employee';
import { IEmployee } from 'app/shared/model/employee.model';

@Component({
    selector: 'jhi-leave-application-extended',
    templateUrl: './leave-application-extended.component.html'
})
export class LeaveApplicationExtendedComponent extends LeaveApplicationComponent implements OnInit {
    employee: IEmployee;

    constructor(
        protected leaveApplicationService: LeaveApplicationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeService
    ) {
        super(leaveApplicationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.loadAll();
        });
        this.registerChangeInLeaveApplications();
    }

    loadAll() {
        this.employeeService
            .query({
                'employeeId.equals': this.currentAccount.login
            })
            .subscribe(
                (res: HttpResponse<IEmployee[]>) => {
                    this.employee = res.body[0];
                    this.leaveApplicationService
                        .query({
                            page: this.page,
                            size: this.itemsPerPage,
                            sort: this.sort(),
                            'employeesId.equals': this.employee.id
                        })
                        .subscribe(
                            (res: HttpResponse<ILeaveApplication[]>) => this.paginateLeaveApplications(res.body, res.headers),
                            (res: HttpErrorResponse) => this.onError(res.message)
                        );
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
}
