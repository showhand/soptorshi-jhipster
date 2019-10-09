import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISystemAccountMap } from 'app/shared/model/system-account-map.model';
import { SystemAccountMapExtendedService } from './system-account-map-extended.service';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';
import { SystemAccountMapUpdateComponent } from 'app/entities/system-account-map';

@Component({
    selector: 'jhi-system-account-map-update',
    templateUrl: './system-account-map-extended-update.component.html'
})
export class SystemAccountMapExtendedUpdateComponent extends SystemAccountMapUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected systemAccountMapService: SystemAccountMapExtendedService,
        protected mstAccountService: MstAccountService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, systemAccountMapService, mstAccountService, activatedRoute);
    }
}
