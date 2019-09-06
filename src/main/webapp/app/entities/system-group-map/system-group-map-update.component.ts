import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISystemGroupMap } from 'app/shared/model/system-group-map.model';
import { SystemGroupMapService } from './system-group-map.service';
import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupService } from 'app/entities/mst-group';

@Component({
    selector: 'jhi-system-group-map-update',
    templateUrl: './system-group-map-update.component.html'
})
export class SystemGroupMapUpdateComponent implements OnInit {
    systemGroupMap: ISystemGroupMap;
    isSaving: boolean;

    mstgroups: IMstGroup[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected systemGroupMapService: SystemGroupMapService,
        protected mstGroupService: MstGroupService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ systemGroupMap }) => {
            this.systemGroupMap = systemGroupMap;
        });
        this.mstGroupService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMstGroup[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMstGroup[]>) => response.body)
            )
            .subscribe((res: IMstGroup[]) => (this.mstgroups = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.systemGroupMap.id !== undefined) {
            this.subscribeToSaveResponse(this.systemGroupMapService.update(this.systemGroupMap));
        } else {
            this.subscribeToSaveResponse(this.systemGroupMapService.create(this.systemGroupMap));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISystemGroupMap>>) {
        result.subscribe((res: HttpResponse<ISystemGroupMap>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMstGroupById(index: number, item: IMstGroup) {
        return item.id;
    }
}
