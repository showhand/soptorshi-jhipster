/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { VendorContactPersonDeleteDialogComponent } from 'app/entities/vendor-contact-person/vendor-contact-person-delete-dialog.component';
import { VendorContactPersonService } from 'app/entities/vendor-contact-person/vendor-contact-person.service';

describe('Component Tests', () => {
    describe('VendorContactPerson Management Delete Component', () => {
        let comp: VendorContactPersonDeleteDialogComponent;
        let fixture: ComponentFixture<VendorContactPersonDeleteDialogComponent>;
        let service: VendorContactPersonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [VendorContactPersonDeleteDialogComponent]
            })
                .overrideTemplate(VendorContactPersonDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VendorContactPersonDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendorContactPersonService);
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
