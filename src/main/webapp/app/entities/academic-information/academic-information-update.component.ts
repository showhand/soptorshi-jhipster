import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAcademicInformation } from 'app/shared/model/academic-information.model';
import { AcademicInformationService } from './academic-information.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-academic-information-update',
    templateUrl: './academic-information-update.component.html'
})
export class AcademicInformationUpdateComponent implements OnInit {
    academicInformation: IAcademicInformation;
    isSaving: boolean;

    employees: IEmployee[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected academicInformationService: AcademicInformationService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ academicInformation }) => {
            this.academicInformation = academicInformation;
            console.log('got academic information');
            console.log(this.academicInformation);
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
        if (this.academicInformation.id !== undefined) {
            this.subscribeToSaveResponse(this.academicInformationService.update(this.academicInformation));
        } else {
            this.subscribeToSaveResponse(this.academicInformationService.create(this.academicInformation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcademicInformation>>) {
        result.subscribe((res: HttpResponse<IAcademicInformation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
