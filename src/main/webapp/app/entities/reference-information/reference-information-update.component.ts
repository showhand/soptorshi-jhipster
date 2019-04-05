import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IReferenceInformation } from 'app/shared/model/reference-information.model';
import { ReferenceInformationService } from './reference-information.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-reference-information-update',
    templateUrl: './reference-information-update.component.html'
})
export class ReferenceInformationUpdateComponent implements OnInit {
    referenceInformation: IReferenceInformation;
    isSaving: boolean;

    employees: IEmployee[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected referenceInformationService: ReferenceInformationService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ referenceInformation }) => {
            this.referenceInformation = referenceInformation;
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
        if (this.referenceInformation.id !== undefined) {
            this.subscribeToSaveResponse(this.referenceInformationService.update(this.referenceInformation));
        } else {
            this.subscribeToSaveResponse(this.referenceInformationService.create(this.referenceInformation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReferenceInformation>>) {
        result.subscribe(
            (res: HttpResponse<IReferenceInformation>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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
