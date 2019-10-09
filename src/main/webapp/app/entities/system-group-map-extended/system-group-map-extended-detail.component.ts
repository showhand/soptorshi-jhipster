import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISystemGroupMap } from 'app/shared/model/system-group-map.model';
import { SystemGroupMapDetailComponent } from 'app/entities/system-group-map';

@Component({
    selector: 'jhi-system-group-map-detail',
    templateUrl: './system-group-map-extended-detail.component.html'
})
export class SystemGroupMapExtendedDetailComponent extends SystemGroupMapDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
