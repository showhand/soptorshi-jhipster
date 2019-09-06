import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISystemAccountMap } from 'app/shared/model/system-account-map.model';
import { SystemAccountMapService } from './system-account-map.service';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';

@Component({
    selector: 'jhi-system-account-map-update',
    templateUrl: './system-account-map-update.component.html'
})
export class SystemAccountMapUpdateComponent implements OnInit {
    systemAccountMap: ISystemAccountMap;
    isSaving: boolean;

    mstaccounts: IMstAccount[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected systemAccountMapService: SystemAccountMapService,
        protected mstAccountService: MstAccountService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ systemAccountMap }) => {
            this.systemAccountMap = systemAccountMap;
        });
        this.mstAccountService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMstAccount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMstAccount[]>) => response.body)
            )
            .subscribe((res: IMstAccount[]) => (this.mstaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.systemAccountMap.id !== undefined) {
            this.subscribeToSaveResponse(this.systemAccountMapService.update(this.systemAccountMap));
        } else {
            this.subscribeToSaveResponse(this.systemAccountMapService.create(this.systemAccountMap));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISystemAccountMap>>) {
        result.subscribe((res: HttpResponse<ISystemAccountMap>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMstAccountById(index: number, item: IMstAccount) {
        return item.id;
    }
}
