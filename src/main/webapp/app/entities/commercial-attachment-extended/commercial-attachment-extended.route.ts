import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { CommercialAttachmentExtendedService } from './commercial-attachment-extended.service';
import { CommercialAttachmentExtendedComponent } from './commercial-attachment-extended.component';
import { CommercialAttachmentDetailExtendedComponent } from './commercial-attachment-detail-extended.component';
import { CommercialAttachmentUpdateExtendedComponent } from './commercial-attachment-update-extended.component';
import { CommercialAttachmentResolve } from 'app/entities/commercial-attachment';
import { CommercialAttachmentDeletePopupExtendedComponent } from 'app/entities/commercial-attachment-extended/commercial-attachment-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialAttachmentExtendedResolve extends CommercialAttachmentResolve {
    constructor(service: CommercialAttachmentExtendedService) {
        super(service);
    }
}

export const commercialAttachmentExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialAttachmentExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialAttachmentDetailExtendedComponent,
        resolve: {
            commercialAttachment: CommercialAttachmentExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialAttachmentUpdateExtendedComponent,
        resolve: {
            commercialAttachment: CommercialAttachmentExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialAttachmentUpdateExtendedComponent,
        resolve: {
            commercialAttachment: CommercialAttachmentExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialAttachments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialAttachmentPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialAttachmentDeletePopupExtendedComponent,
        resolve: {
            commercialAttachment: CommercialAttachmentExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
