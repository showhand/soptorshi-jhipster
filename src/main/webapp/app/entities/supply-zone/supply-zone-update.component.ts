import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { SupplyZoneService } from './supply-zone.service';

@Component({
    selector: 'jhi-supply-zone-update',
    templateUrl: './supply-zone-update.component.html'
})
export class SupplyZoneUpdateComponent implements OnInit {
    supplyZone: ISupplyZone;
    isSaving: boolean;
    createdOn: string;
    updatedOn: string;

    constructor(protected supplyZoneService: SupplyZoneService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyZone }) => {
            this.supplyZone = supplyZone;
            this.createdOn = this.supplyZone.createdOn != null ? this.supplyZone.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.supplyZone.updatedOn != null ? this.supplyZone.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.supplyZone.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.supplyZone.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.supplyZone.id !== undefined) {
            this.subscribeToSaveResponse(this.supplyZoneService.update(this.supplyZone));
        } else {
            this.subscribeToSaveResponse(this.supplyZoneService.create(this.supplyZone));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplyZone>>) {
        result.subscribe((res: HttpResponse<ISupplyZone>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
