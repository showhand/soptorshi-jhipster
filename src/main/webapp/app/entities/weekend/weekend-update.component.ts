import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IWeekend } from 'app/shared/model/weekend.model';
import { WeekendService } from './weekend.service';

@Component({
    selector: 'jhi-weekend-update',
    templateUrl: './weekend-update.component.html'
})
export class WeekendUpdateComponent implements OnInit {
    weekend: IWeekend;
    isSaving: boolean;
    activeFromDp: any;
    activeToDp: any;
    createdOn: string;
    updatedOn: string;

    constructor(protected weekendService: WeekendService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ weekend }) => {
            this.weekend = weekend;
            this.createdOn = this.weekend.createdOn != null ? this.weekend.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.weekend.updatedOn != null ? this.weekend.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.weekend.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.weekend.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.weekend.id !== undefined) {
            this.subscribeToSaveResponse(this.weekendService.update(this.weekend));
        } else {
            this.subscribeToSaveResponse(this.weekendService.create(this.weekend));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IWeekend>>) {
        result.subscribe((res: HttpResponse<IWeekend>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
