import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { forkJoin, Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IDepreciationMap } from 'app/shared/model/depreciation-map.model';
import { DepreciationMapService } from './depreciation-map.service';
import { AccountService } from 'app/core';
import { MstAccountExtendedService } from 'app/entities/mst-account-extended';
import { IMstAccount } from 'app/shared/model/mst-account.model';

@Component({
    selector: 'jhi-depreciation-map-update',
    templateUrl: './depreciation-map-update.component.html'
})
export class DepreciationMapUpdateComponent implements OnInit {
    depreciationMap: IDepreciationMap;
    isSaving: boolean;
    createdOn: string;
    modifiedOn: string;
    accounts: IMstAccount[];
    accountIdMap: Record<number, IMstAccount>;

    constructor(
        protected depreciationMapService: DepreciationMapService,
        protected activatedRoute: ActivatedRoute,
        private accountService: MstAccountExtendedService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ depreciationMap }) => {
            this.depreciationMap = depreciationMap;
            this.createdOn = this.depreciationMap.createdOn != null ? this.depreciationMap.createdOn.format(DATE_TIME_FORMAT) : null;
            this.modifiedOn = this.depreciationMap.modifiedOn != null ? this.depreciationMap.modifiedOn.format(DATE_TIME_FORMAT) : null;
        });

        this.accountService
            .query({
                size: 5000
            })
            .subscribe(res => {
                this.accounts = res.body;
                this.accountIdMap = {};
                this.accounts.forEach((a: IMstAccount) => {
                    this.accountIdMap[a.id] = a;
                });
            });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.depreciationMap.accountId) this.depreciationMap.accountName = this.accountIdMap[this.depreciationMap.accountId].name;
        if (this.depreciationMap.depreciationAccountId)
            this.depreciationMap.depreciationAccountName = this.accountIdMap[this.depreciationMap.depreciationAccountId].name;
        this.depreciationMap.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.depreciationMap.modifiedOn = this.modifiedOn != null ? moment(this.modifiedOn, DATE_TIME_FORMAT) : null;
        if (this.depreciationMap.id !== undefined) {
            this.subscribeToSaveResponse(this.depreciationMapService.update(this.depreciationMap));
        } else {
            this.subscribeToSaveResponse(this.depreciationMapService.create(this.depreciationMap));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepreciationMap>>) {
        result.subscribe((res: HttpResponse<IDepreciationMap>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
