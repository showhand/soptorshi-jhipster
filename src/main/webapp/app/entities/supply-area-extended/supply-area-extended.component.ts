import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyAreaExtendedService } from './supply-area-extended.service';
import { SupplyAreaComponent } from 'app/entities/supply-area';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyZoneManager, SupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';

@Component({
    selector: 'jhi-supply-area-extended',
    templateUrl: './supply-area-extended.component.html'
})
export class SupplyAreaExtendedComponent extends SupplyAreaComponent implements OnInit {
    supplyzonemanagers: SupplyZoneManager[];

    constructor(
        protected supplyAreaService: SupplyAreaExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService
    ) {
        super(supplyAreaService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.supplyZoneManagerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZoneManager[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZoneManager[]>) => response.body)
            )
            .subscribe(
                (res: ISupplyZoneManager[]) => (this.supplyzonemanagers = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.registerChangeInSupplyAreas();
    }
}
