import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITrainingInformation } from 'app/shared/model/training-information.model';
import { TrainingInformationService } from './training-information.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-training-information-update',
    templateUrl: './training-information-update.component.html'
})
export class TrainingInformationUpdateComponent implements OnInit {
    @Input()
    trainingInformation: ITrainingInformation;
    @Output()
    showTrainingInformationSection: EventEmitter<any> = new EventEmitter();
    isSaving: boolean;

    employees: IEmployee[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected trainingInformationService: TrainingInformationService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {}

    previousState() {
        this.showTrainingInformationSection.emit();
    }

    save() {
        this.isSaving = true;
        if (this.trainingInformation.id !== undefined) {
            this.subscribeToSaveResponse(this.trainingInformationService.update(this.trainingInformation));
        } else {
            this.subscribeToSaveResponse(this.trainingInformationService.create(this.trainingInformation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingInformation>>) {
        result.subscribe((res: HttpResponse<ITrainingInformation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
