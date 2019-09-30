import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from './mst-account.service';
import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupService } from 'app/entities/mst-group';

@Component({
    selector: 'jhi-mst-account-update',
    templateUrl: './mst-account-update.component.html'
})
export class MstAccountUpdateComponent implements OnInit {
    mstAccount: IMstAccount;
    isSaving: boolean;

    mstgroups: IMstGroup[];
    modifiedOnDp: any;
    selectedGroupName: string;
    groupNameMapId: any;
    groupIdMapName: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected mstAccountService: MstAccountService,
        protected mstGroupService: MstGroupService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mstAccount }) => {
            this.mstAccount = mstAccount;
            if (this.mstAccount.groupName) this.selectedGroupName = this.mstAccount.groupName;
        });
        this.mstGroupService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMstGroup[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMstGroup[]>) => response.body)
            )
            .subscribe(
                (res: IMstGroup[]) => {
                    this.mstgroups = res;
                    this.groupIdMapName = {};
                    this.groupNameMapId = {};
                    res.forEach((g: IMstGroup) => {
                        this.groupNameMapId[g.name] = g.id;
                        this.groupIdMapName[g.id] = g.name;
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.mstAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.mstAccountService.update(this.mstAccount));
        } else {
            this.subscribeToSaveResponse(this.mstAccountService.create(this.mstAccount));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMstAccount>>) {
        result.subscribe((res: HttpResponse<IMstAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
