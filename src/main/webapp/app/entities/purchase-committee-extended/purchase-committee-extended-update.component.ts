import { PurchaseCommitteeService, PurchaseCommitteeUpdateComponent } from 'app/entities/purchase-committee';
import { JhiAlertService } from 'ng-jhipster';
import { EmployeeService } from 'app/entities/employee';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { IEmployee } from 'app/shared/model/employee.model';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-purchase-committee-extended-update',
    templateUrl: './purchase-committee-extended-update.component.html'
})
export class PurchaseCommitteeExtendedUpdateComponent extends PurchaseCommitteeUpdateComponent implements OnInit {
    selectedEmployee: IEmployee;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected purchaseCommitteeService: PurchaseCommitteeService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, purchaseCommitteeService, employeeService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseCommittee }) => {
            this.purchaseCommittee = purchaseCommittee;
        });
        this.employeeService
            .query({ 'purchaseCommitteeId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe(
                (res: IEmployee[]) => {
                    if (!this.purchaseCommittee.employeeId) {
                        this.employees = res;
                    } else {
                        this.employeeService
                            .find(this.purchaseCommittee.employeeId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IEmployee>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IEmployee>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IEmployee) => {
                                    this.employees = res;
                                    this.selectedEmployee = this.employees[0];
                                },
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    save() {
        this.isSaving = true;
        this.purchaseCommittee.employeeId = this.selectedEmployee.id;
        if (this.purchaseCommittee.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseCommitteeService.update(this.purchaseCommittee));
        } else {
            this.subscribeToSaveResponse(this.purchaseCommitteeService.create(this.purchaseCommittee));
        }
    }

    filterEmployees(event) {
        this.employeeService
            .query({
                'fullName.contains': event.query.toString()
            })
            .subscribe((res: HttpResponse<IEmployee[]>) => {
                this.employees = [];
                this.employees = res.body;
            });
    }
}
