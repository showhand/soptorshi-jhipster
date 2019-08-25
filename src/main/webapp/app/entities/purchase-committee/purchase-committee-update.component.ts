import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { debounceTime, filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPurchaseCommittee } from 'app/shared/model/purchase-committee.model';
import { PurchaseCommitteeService } from './purchase-committee.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { Select2OptionData } from 'ng2-select2';

@Component({
    selector: 'jhi-purchase-committee-update',
    templateUrl: './purchase-committee-update.component.html'
})
export class PurchaseCommitteeUpdateComponent implements OnInit {
    purchaseCommittee: IPurchaseCommittee;
    isSaving: boolean;
    selectedEmployee: IEmployee;
    employees: IEmployee[];

    focus$ = new Subject<IEmployee>();

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
            .query({ 'purchaseCommitteeId.specified': 'false', size: 100000 })
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

    previousState() {
        window.history.back();
    }

    formatter = (x: { fullName: string }) => x.fullName;

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

    employeeSelected(event) {
        this.purchaseCommittee.employeeId = event.item.id;
    }

    search = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            map(term =>
                term === '' ? [] : this.employees.filter(v => v.fullName.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)
            )
        );
}
