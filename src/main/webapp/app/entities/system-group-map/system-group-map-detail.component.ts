import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISystemGroupMap } from 'app/shared/model/system-group-map.model';

@Component({
    selector: 'jhi-system-group-map-detail',
    templateUrl: './system-group-map-detail.component.html'
})
export class SystemGroupMapDetailComponent implements OnInit {
    systemGroupMap: ISystemGroupMap;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ systemGroupMap }) => {
            this.systemGroupMap = systemGroupMap;
        });
    }

    previousState() {
        window.history.back();
    }
}
