import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { merge, Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupService } from 'app/entities/mst-group';
import { MstAccountExtendedService } from 'app/entities/mst-account-extended/mst-account-extended.service';
import { MstAccountService, MstAccountUpdateComponent } from 'app/entities/mst-account';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-mst-account-extended-update',
    templateUrl: './mst-account-extended-update.component.html'
})
export class MstAccountExtendedUpdateComponent extends MstAccountUpdateComponent implements OnInit {
    selectedGroupName: string;
    groupNameMapId: any;
    groupIdMapName: any;
    groupNameList: string[];

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected mstAccountService: MstAccountService,
        protected mstGroupService: MstGroupService,
        protected activatedRoute: ActivatedRoute,
        protected mstAccountExtendedService: MstAccountExtendedService
    ) {
        super(jhiAlertService, mstAccountService, mstGroupService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mstAccount }) => {
            this.mstAccount = mstAccount;
            if (this.mstAccount.groupName) {
                this.selectedGroupName = this.mstAccount.groupName;
            } else {
                this.selectedGroupName = '';
            }
        });
        this.mstGroupService
            .query({
                size: 2000
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IMstGroup[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMstGroup[]>) => response.body)
            )
            .subscribe(
                (res: IMstGroup[]) => {
                    this.mstgroups = res;
                    this.groupIdMapName = {};
                    this.groupNameMapId = {};
                    this.groupNameList = [];
                    res.forEach((g: IMstGroup) => {
                        this.groupNameMapId[g.name] = g.id;
                        this.groupIdMapName[g.id] = g.name;
                        this.groupNameList.push(g.name);
                    });
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search = (text$: Observable<string>) => {
        const debouncedText$ = text$.pipe(
            debounceTime(200),
            distinctUntilChanged()
        );
        const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
        const inputFocus$ = this.focus$;

        return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
            map(term =>
                (term === '' ? this.groupNameList : this.groupNameList.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(
                    0,
                    10
                )
            )
        );
    };

    save() {
        console.log('selected group name-->' + this.selectedGroupName);
        if (this.selectedGroupName == undefined) {
            this.jhiAlertService.error('Error');
        } else {
            this.isSaving = true;
            this.mstAccount.groupId = this.groupNameMapId[this.selectedGroupName];
            this.mstAccount.groupName = this.selectedGroupName;
            if (this.mstAccount.id !== undefined) {
                this.subscribeToSaveResponse(this.mstAccountExtendedService.update(this.mstAccount));
            } else {
                this.subscribeToSaveResponse(this.mstAccountExtendedService.create(this.mstAccount));
            }
        }
    }
}
