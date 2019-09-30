import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupService } from 'app/entities/mst-group';
import { MstAccountExtendedService } from 'app/entities/mst-account-extended/mst-account-extended.service';
import { MstAccountUpdateComponent } from 'app/entities/mst-account';

@Component({
    selector: 'jhi-mst-account-extended-update',
    templateUrl: './mst-account-extended-update.component.html'
})
export class MstAccountExtendedUpdateComponent extends MstAccountUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected mstAccountService: MstAccountExtendedService,
        protected mstGroupService: MstGroupService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, mstAccountService, mstGroupService, activatedRoute);
    }
}
