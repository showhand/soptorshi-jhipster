import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerDetailComponent } from 'app/entities/manufacturer';

@Component({
    selector: 'jhi-manufacturer-detail-extended',
    templateUrl: './manufacturer-detail-extended.component.html'
})
export class ManufacturerDetailExtendedComponent extends ManufacturerDetailComponent implements OnInit {
    manufacturer: IManufacturer;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ manufacturer }) => {
            this.manufacturer = manufacturer;
        });
    }

    previousState() {
        window.history.back();
    }
}
