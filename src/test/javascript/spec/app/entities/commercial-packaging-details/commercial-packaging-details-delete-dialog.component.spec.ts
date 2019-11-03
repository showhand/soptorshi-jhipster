/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPackagingDetailsDeleteDialogComponent } from 'app/entities/commercial-packaging-details/commercial-packaging-details-delete-dialog.component';
import { CommercialPackagingDetailsService } from 'app/entities/commercial-packaging-details/commercial-packaging-details.service';

describe('Component Tests', () => {
    describe('CommercialPackagingDetails Management Delete Component', () => {
        let comp: CommercialPackagingDetailsDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialPackagingDetailsDeleteDialogComponent>;
        let service: CommercialPackagingDetailsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPackagingDetailsDeleteDialogComponent]
            })
                .overrideTemplate(CommercialPackagingDetailsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPackagingDetailsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPackagingDetailsService);
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
