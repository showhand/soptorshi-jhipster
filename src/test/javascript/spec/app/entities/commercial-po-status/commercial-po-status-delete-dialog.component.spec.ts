/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPoStatusDeleteDialogComponent } from 'app/entities/commercial-po-status/commercial-po-status-delete-dialog.component';
import { CommercialPoStatusService } from 'app/entities/commercial-po-status/commercial-po-status.service';

describe('Component Tests', () => {
    describe('CommercialPoStatus Management Delete Component', () => {
        let comp: CommercialPoStatusDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialPoStatusDeleteDialogComponent>;
        let service: CommercialPoStatusService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPoStatusDeleteDialogComponent]
            })
                .overrideTemplate(CommercialPoStatusDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPoStatusDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPoStatusService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
