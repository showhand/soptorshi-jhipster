/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPackagingDeleteDialogComponent } from 'app/entities/commercial-packaging/commercial-packaging-delete-dialog.component';
import { CommercialPackagingService } from 'app/entities/commercial-packaging/commercial-packaging.service';

describe('Component Tests', () => {
    describe('CommercialPackaging Management Delete Component', () => {
        let comp: CommercialPackagingDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialPackagingDeleteDialogComponent>;
        let service: CommercialPackagingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPackagingDeleteDialogComponent]
            })
                .overrideTemplate(CommercialPackagingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPackagingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPackagingService);
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
