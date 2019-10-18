import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ManufacturerComponentExtended,
    ManufacturerDetailComponentExtended,
    ManufacturerUpdateComponentExtended,
    ManufacturerDeletePopupComponentExtended,
    ManufacturerDeleteDialogComponentExtended,
    manufacturerRouteExtended,
    manufacturerPopupRouteExtended
} from './';

const ENTITY_STATES = [...manufacturerRouteExtended, ...manufacturerPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ManufacturerComponentExtended,
        ManufacturerDetailComponentExtended,
        ManufacturerUpdateComponentExtended,
        ManufacturerDeleteDialogComponentExtended,
        ManufacturerDeletePopupComponentExtended
    ],
    entryComponents: [
        ManufacturerComponentExtended,
        ManufacturerUpdateComponentExtended,
        ManufacturerDeleteDialogComponentExtended,
        ManufacturerDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiManufacturerModuleExtended {}
