import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialAttachment, ICommercialAttachment } from 'app/shared/model/commercial-attachment.model';
import { CommercialAttachmentExtendedService } from './commercial-attachment-extended.service';
import { CommercialAttachmentExtendedComponent } from './commercial-attachment-extended.component';
import { CommercialAttachmentDetailExtendedComponent } from './commercial-attachment-detail-extended.component';
import { CommercialAttachmentUpdateExtendedComponent } from './commercial-attachment-update-extended.component';
import { CommercialAttachmentDeletePopupExtendedComponent } from './commercial-attachment-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialAttachmentExtendedResolve implements Resolve<ICommercialAttachment> {
    constructor(private service: CommercialAttachmentExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialAttachment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialAttachment>) => response.ok),
                map((commercialAttachment: HttpResponse<CommercialAttachment>) => commercialAttachment.body)
            );
        }
        return of(new CommercialAttachment());
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
