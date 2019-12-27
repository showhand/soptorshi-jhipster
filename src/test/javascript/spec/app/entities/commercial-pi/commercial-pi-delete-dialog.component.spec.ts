/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPiDeleteDialogComponent } from 'app/entities/commercial-pi/commercial-pi-delete-dialog.component';
import { CommercialPiService } from 'app/entities/commercial-pi/commercial-pi.service';

describe('Component Tests', () => {
    describe('CommercialPi Management Delete Component', () => {
        let comp: CommercialPiDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialPiDeleteDialogComponent>;
        let service: CommercialPiService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPiDeleteDialogComponent]
            })
                .overrideTemplate(CommercialPiDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPiDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPiService);
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
