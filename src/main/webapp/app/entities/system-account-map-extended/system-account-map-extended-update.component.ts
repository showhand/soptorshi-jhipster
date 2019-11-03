import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { merge, Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISystemAccountMap } from 'app/shared/model/system-account-map.model';
import { SystemAccountMapExtendedService } from './system-account-map-extended.service';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';
import { SystemAccountMapUpdateComponent } from 'app/entities/system-account-map';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { GroupType } from 'app/shared/model/system-group-map.model';

@Component({
    selector: 'jhi-system-account-map-update',
    templateUrl: './system-account-map-extended-update.component.html'
})
export class SystemAccountMapExtendedUpdateComponent extends SystemAccountMapUpdateComponent implements OnInit {
    groupTypeWithSystemGroupMap: any;
    accountNameList: string[] = [];
    accountNameMapAccount: any;
    selectedAccountName: string;

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected systemAccountMapService: SystemAccountMapExtendedService,
        protected mstAccountService: MstAccountService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, systemAccountMapService, mstAccountService, activatedRoute);
    }

    accountSelected() {
        const selectedAccount: IMstAccount = this.accountNameMapAccount[this.selectedAccountName];
        this.systemAccountMap.accountId = selectedAccount.id;
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ systemAccountMap }) => {
            this.systemAccountMap = systemAccountMap;
        });
        this.mstaccounts = [];
        this.mstAccountService
            .query({
                size: 5000
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IMstAccount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMstAccount[]>) => response.body)
            )
            .subscribe((res: IMstAccount[]) => {
                this.mstaccounts = res;
                this.accountNameList = [];
                this.accountNameMapAccount = {};
                this.mstaccounts.forEach((a: IMstAccount) => {
                    const accountName = a.name + ' (' + a.groupName + ')';
                    this.accountNameList.push(accountName);
                    this.accountNameMapAccount[accountName] = a;
                    if (a.id == this.systemAccountMap.accountId) this.selectedAccountName = accountName;
                });
            });
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
                (term === ''
                    ? this.accountNameList
                    : this.accountNameList.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)
                ).slice(0, 10)
            )
        );
    };
}
