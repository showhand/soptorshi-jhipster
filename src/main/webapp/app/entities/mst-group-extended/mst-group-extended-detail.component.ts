import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupDetailComponent } from 'app/entities/mst-group';

@Component({
    selector: 'jhi-mst-group-extended-detail',
    templateUrl: './mst-group-extended-detail.component.html'
})
export class MstGroupExtendedDetailComponent extends MstGroupDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
