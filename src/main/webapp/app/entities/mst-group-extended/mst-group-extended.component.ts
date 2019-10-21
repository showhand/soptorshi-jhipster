import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IMstGroup } from 'app/shared/model/mst-group.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { MstGroupExtendedService } from './mst-group-extended.service';
import { MstGroupComponent } from 'app/entities/mst-group';

@Component({
    selector: 'jhi-mst-group-extended',
    templateUrl: './mst-group-extended.component.html'
})
export class MstGroupExtendedComponent extends MstGroupComponent implements OnInit, OnDestroy {
    groupIdMapName: any;

    constructor(
        protected mstGroupService: MstGroupExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(mstGroupService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    ngOnInit() {
        super.ngOnInit();
        this.mstGroupService
            .query({
                size: 2000
            })
            .subscribe((response: HttpResponse<IMstGroup[]>) => {
                const groups = response.body;
                this.groupIdMapName = {};
                groups.forEach((g: IMstGroup) => {
                    this.groupIdMapName[g.id] = g.name;
                });
            });
    }

    loadAll() {
        if (this.currentSearch) {
            this.mstGroupService
                .query({
                    'name.contains': this.currentSearch,
                    page: this.page,
                    size: this.itemsPerPage
                })
                .subscribe(
                    (res: HttpResponse<IMstGroup[]>) => this.paginateMstGroups(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.mstGroupService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IMstGroup[]>) => this.paginateMstGroups(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
}
