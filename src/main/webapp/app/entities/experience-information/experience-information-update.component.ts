import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IExperienceInformation } from 'app/shared/model/experience-information.model';
import { ExperienceInformationService } from './experience-information.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-experience-information-update',
    templateUrl: './experience-information-update.component.html'
})
export class ExperienceInformationUpdateComponent implements OnInit {
    @Input()
    experienceInformation: IExperienceInformation;
    @Output()
    showExperienceInformationSection: EventEmitter<any> = new EventEmitter();
    isSaving: boolean;

    employees: IEmployee[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected experienceInformationService: ExperienceInformationService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
    }

    previousState() {
        this.showExperienceInformationSection.emit();
    }

    save() {
        this.isSaving = true;
        if (this.experienceInformation.id !== undefined) {
            this.subscribeToSaveResponse(this.experienceInformationService.update(this.experienceInformation));
        } else {
            this.subscribeToSaveResponse(this.experienceInformationService.create(this.experienceInformation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExperienceInformation>>) {
        result.subscribe(
            (res: HttpResponse<IExperienceInformation>) => this.onSaveSuccess(),
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
