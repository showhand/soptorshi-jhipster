import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProvidentFund } from 'app/shared/model/provident-fund.model';
import { ProvidentFundService } from './provident-fund.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-provident-fund-update',
    templateUrl: './provident-fund-update.component.html'
})
export class ProvidentFundUpdateComponent implements OnInit {
    providentFund: IProvidentFund;
    isSaving: boolean;

    employees: IEmployee[];
    startDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected providentFundService: ProvidentFundService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ providentFund }) => {
            this.providentFund = providentFund;
        });
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.providentFund.id !== undefined) {
            this.subscribeToSaveResponse(this.providentFundService.update(this.providentFund));
        } else {
            this.subscribeToSaveResponse(this.providentFundService.create(this.providentFund));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvidentFund>>) {
        result.subscribe((res: HttpResponse<IProvidentFund>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
