/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialProductInfoDeleteDialogComponent } from 'app/entities/commercial-product-info/commercial-product-info-delete-dialog.component';
import { CommercialProductInfoService } from 'app/entities/commercial-product-info/commercial-product-info.service';

describe('Component Tests', () => {
    describe('CommercialProductInfo Management Delete Component', () => {
        let comp: CommercialProductInfoDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialProductInfoDeleteDialogComponent>;
        let service: CommercialProductInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialProductInfoDeleteDialogComponent]
            })
                .overrideTemplate(CommercialProductInfoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialProductInfoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialProductInfoService);
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
