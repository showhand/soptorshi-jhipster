import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ManufacturerDeleteDialogExtendedComponent,
    ManufacturerDeletePopupExtendedComponent,
    ManufacturerDetailExtendedComponent,
    ManufacturerExtendedComponent,
    manufacturerExtendedRoute,
    manufacturerPopupRouteExtended,
    ManufacturerUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...manufacturerExtendedRoute, ...manufacturerPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ManufacturerExtendedComponent,
        ManufacturerDetailExtendedComponent,
        ManufacturerUpdateExtendedComponent,
        ManufacturerDeleteDialogExtendedComponent,
        ManufacturerDeletePopupExtendedComponent
    ],
    entryComponents: [
        ManufacturerExtendedComponent,
        ManufacturerUpdateExtendedComponent,
        ManufacturerDeleteDialogExtendedComponent,
        ManufacturerDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiManufacturerModuleExtended {}
