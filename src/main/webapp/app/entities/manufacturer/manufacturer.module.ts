import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ManufacturerComponent,
    ManufacturerDetailComponent,
    ManufacturerUpdateComponent,
    ManufacturerDeletePopupComponent,
    ManufacturerDeleteDialogComponent,
    manufacturerRoute,
    manufacturerPopupRoute
} from './';

const ENTITY_STATES = [...manufacturerRoute, ...manufacturerPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ManufacturerComponent,
        ManufacturerDetailComponent,
        ManufacturerUpdateComponent,
        ManufacturerDeleteDialogComponent,
        ManufacturerDeletePopupComponent
    ],
    entryComponents: [
        ManufacturerComponent,
        ManufacturerUpdateComponent,
        ManufacturerDeleteDialogComponent,
        ManufacturerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiManufacturerModule {}
