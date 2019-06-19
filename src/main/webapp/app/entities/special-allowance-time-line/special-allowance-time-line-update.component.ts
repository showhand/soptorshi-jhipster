import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ISpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';
import { SpecialAllowanceTimeLineService } from './special-allowance-time-line.service';

@Component({
    selector: 'jhi-special-allowance-time-line-update',
    templateUrl: './special-allowance-time-line-update.component.html'
})
export class SpecialAllowanceTimeLineUpdateComponent implements OnInit {
    specialAllowanceTimeLine: ISpecialAllowanceTimeLine;
    isSaving: boolean;
    modifiedOnDp: any;

    constructor(protected specialAllowanceTimeLineService: SpecialAllowanceTimeLineService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ specialAllowanceTimeLine }) => {
            this.specialAllowanceTimeLine = specialAllowanceTimeLine;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.specialAllowanceTimeLine.id !== undefined) {
            this.subscribeToSaveResponse(this.specialAllowanceTimeLineService.update(this.specialAllowanceTimeLine));
        } else {
            this.subscribeToSaveResponse(this.specialAllowanceTimeLineService.create(this.specialAllowanceTimeLine));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialAllowanceTimeLine>>) {
        result.subscribe(
            (res: HttpResponse<ISpecialAllowanceTimeLine>) => this.onSaveSuccess(),
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
}
