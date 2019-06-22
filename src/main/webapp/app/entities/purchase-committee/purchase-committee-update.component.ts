import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPurchaseCommittee } from 'app/shared/model/purchase-committee.model';
import { PurchaseCommitteeService } from './purchase-committee.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-purchase-committee-update',
    templateUrl: './purchase-committee-update.component.html'
})
export class PurchaseCommitteeUpdateComponent implements OnInit {
    purchaseCommittee: IPurchaseCommittee;
    isSaving: boolean;

    employees: IEmployee[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected purchaseCommitteeService: PurchaseCommitteeService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

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
                                (subRes: IEmployee) => (this.employees = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.purchaseCommittee.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseCommitteeService.update(this.purchaseCommittee));
        } else {
            this.subscribeToSaveResponse(this.purchaseCommitteeService.create(this.purchaseCommittee));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseCommittee>>) {
        result.subscribe((res: HttpResponse<IPurchaseCommittee>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }
}
