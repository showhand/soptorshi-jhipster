import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupService } from './mst-group.service';

@Component({
    selector: 'jhi-mst-group-update',
    templateUrl: './mst-group-update.component.html'
})
export class MstGroupUpdateComponent implements OnInit {
    mstGroup: IMstGroup;
    isSaving: boolean;
    modifiedOnDp: any;

    constructor(protected mstGroupService: MstGroupService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mstGroup }) => {
            this.mstGroup = mstGroup;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.mstGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.mstGroupService.update(this.mstGroup));
        } else {
            this.subscribeToSaveResponse(this.mstGroupService.create(this.mstGroup));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMstGroup>>) {
        result.subscribe((res: HttpResponse<IMstGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
