import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISystemAccountMap } from 'app/shared/model/system-account-map.model';
import { SystemAccountMapDetailComponent } from 'app/entities/system-account-map';

@Component({
    selector: 'jhi-system-account-map-detail',
    templateUrl: './system-account-map-extended-detail.component.html'
})
export class SystemAccountMapExtendedDetailComponent extends SystemAccountMapDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
