import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountDetailComponent } from 'app/entities/mst-account';

@Component({
    selector: 'jhi-mst-account-extended-detail',
    templateUrl: './mst-account-extended-detail.component.html'
})
export class MstAccountExtendedDetailComponent extends MstAccountDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
