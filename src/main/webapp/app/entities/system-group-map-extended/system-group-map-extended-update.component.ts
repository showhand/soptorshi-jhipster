import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISystemGroupMap } from 'app/shared/model/system-group-map.model';
import { SystemGroupMapExtendedService } from './system-group-map-extended.service';
import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupService } from 'app/entities/mst-group';
import { SystemGroupMapUpdateComponent } from 'app/entities/system-group-map';

@Component({
    selector: 'jhi-system-group-map-update',
    templateUrl: './system-group-map-extended-update.component.html'
})
export class SystemGroupMapExtendedUpdateComponent extends SystemGroupMapUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected systemGroupMapService: SystemGroupMapExtendedService,
        protected mstGroupService: MstGroupService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, systemGroupMapService, mstGroupService, activatedRoute);
    }
}
